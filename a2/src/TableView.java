/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ public class TableView
/*     */   implements View
/*     */ {
/*     */   private HotelManager hotelManager;
/*     */   private Command currentCommand;
/*     */ 
/*     */   public TableView(HotelManager hotelManager)
/*     */   {
/*  16 */     this.hotelManager = hotelManager;
/*     */   }
/*     */ 
/*     */   public JPanel accept(Command command) {
/*  20 */     this.currentCommand = command;
/*  21 */     return command.getPanel(this);
/*     */   }
/*     */ 
/*     */   public JPanel checkInPanel() {
/*  25 */     return new TableView.CheckInPanel();
/*     */   }
/*     */ 
/*     */   public JPanel checkOutPanel() {
/*  29 */     return new TableView.CheckOutPanel();
/*     */   }
/*     */ 
/*     */   public JPanel searchPanel() {
/*  33 */     return new TableView.SearchPanel(); } 
/*  37 */   private class CheckInPanel extends JPanel { private CheckInCommand command = (CheckInCommand)TableView.this.currentCommand;
/*     */     private JTextField IDField;
/*     */     private JTextField nameField;
/*     */     private JComboBox typeField;
/*     */     private JTextField companyField;
/*     */     private JTextField checkInDateField;
/*     */     private JComboBox dataServiceRequiredBox;
/*     */     private JTextField ethernetAddressField;
/*     */     private JTable availableRoomTable;
/*     */     private Object[] availableRooms;
/*     */     private JButton checkInButton;
/*     */ 
/*  50 */     public CheckInPanel() { setLayout(new BorderLayout());
/*  51 */       addOccupantInfo();
/*  52 */       addRoomInfo();
/*  53 */       getCommandValues();
/*  54 */       updateDataServiceRequired(); }
/*     */ 
/*     */     private void addOccupantInfo()
/*     */     {
/*  58 */       JPanel wholePanel = new JPanel();
/*  59 */       JPanel upperPanel = new JPanel();
/*  60 */       JPanel lowerPanel = new JPanel();
/*  61 */       wholePanel.setLayout(new BorderLayout());
/*  62 */       lowerPanel.setLayout(new BorderLayout());
/*     */ 
/*  64 */       add(wholePanel, "East");
/*  65 */       wholePanel.setBorder(new BevelBorder(1));
/*  66 */       wholePanel.add(upperPanel, "Center");
/*  67 */       wholePanel.add(lowerPanel, "South");
/*  68 */       upperPanel.add(getOccupantInfoHelper());
/*  69 */       this.checkInButton = new JButton("Check in");
/*  70 */       this.checkInButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/*  72 */           Container container = TableView.CheckInPanel.this.checkInButton.getTopLevelAncestor();
/*  73 */           if (TableView.CheckInPanel.this.command.getSelectedRoom() == null)
/*     */           {
/*  75 */             JOptionPane.showMessageDialog(container, "No room is selected", 
/*  76 */               "Input Error", 1);
/*  77 */             return;
/*     */           }
/*  79 */           String result = TableView.CheckInPanel.this.command.checkIn();
/*  80 */           if (result.equals("Success"))
/*  81 */             TableView.CheckInPanel.this.getCommandValues();
/*     */           else
/*  83 */             JOptionPane.showMessageDialog(container, result, 
/*  84 */               "Input Error", 1);
/*     */         }
/*     */       });
/*  88 */       lowerPanel.add(this.checkInButton);
/*     */     }
/*     */ 
/*     */     private void getCommandValues() {
/*  92 */       ArrayList list = TableView.this.hotelManager.listAllAvailableRooms();
/*  93 */       this.availableRooms = list.toArray();
/*  94 */       this.availableRoomTable.setModel(new AbstractTableModel() {
/*  95 */         String[] headers = { "Room No", "Room Type", "Capacity", "Rate", "Data Service" };
/*     */ 
/*  96 */         public int getRowCount() { return TableView.CheckInPanel.this.availableRooms.length; } 
/*  97 */         public int getColumnCount() { return this.headers.length; } 
/*     */         public Object getValueAt(int r, int c) {
/*  99 */           Room room = (Room)TableView.CheckInPanel.this.availableRooms[r];
/* 100 */           if (room == null) return null;
/* 101 */           switch (c) { case 0:
/* 102 */             return room.getFloorNo() + "-" + room.getRoomNo();
/*     */           case 1:
/* 103 */             return room.getTypeString();
/*     */           case 2:
/* 104 */             return new Integer(room.getCapacity());
/*     */           case 3:
/* 105 */             return new Double(room.getRate());
/*     */           case 4:
/* 106 */             if (room.getType() == 1) return "N/A";
/* 107 */             return "Not in used";
/*     */           }
/* 109 */           return "";
/*     */         }
/* 111 */         public String getColumnName(int c) { return this.headers[c]; }
/*     */ 
/*     */       });
/* 113 */       Room selectedRoom = this.command.getSelectedRoom();
/* 114 */       if (selectedRoom != null) {
/* 115 */         int index = list.indexOf(selectedRoom);
/* 116 */         this.availableRoomTable.setRowSelectionInterval(index, index);
/*     */       }
/* 118 */       this.IDField.setText(this.command.getID());
/* 119 */       this.nameField.setText(this.command.getName());
/* 120 */       this.typeField.setSelectedItem(this.command.getType());
/* 121 */       this.companyField.setText(this.command.getCompany());
/* 122 */       this.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(this.command.getCheckInDate()));
/* 123 */       updateDataServiceRequired();
/* 124 */       this.ethernetAddressField.setText(this.command.getEthernetAddress());
/*     */     }
/*     */ 
/*     */     private void updateDataServiceRequired() {
/* 128 */       Room room = this.command.getSelectedRoom();
/* 129 */       if ((room == null) || (room.getType() == 1) || (this.typeField.getSelectedItem().equals("Standard"))) {
/* 130 */         this.dataServiceRequiredBox.setEnabled(false);
/* 131 */         this.ethernetAddressField.setEnabled(false);
/* 132 */         if (this.dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
/* 133 */           this.dataServiceRequiredBox.setSelectedItem("No");
/*     */         }
/* 135 */         this.command.setDataServiceRequired(false);
/*     */       } else {
/* 137 */         this.dataServiceRequiredBox.setEnabled(true);
/* 138 */         if (this.command.isDataServiceRequired()) {
/* 139 */           if (this.dataServiceRequiredBox.getSelectedItem().equals("No")) {
/* 140 */             this.dataServiceRequiredBox.setSelectedItem("Yes");
/*     */           }
/* 142 */           this.ethernetAddressField.setEnabled(true);
/*     */         } else {
/* 144 */           if (this.dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
/* 145 */             this.dataServiceRequiredBox.setSelectedItem("No");
/*     */           }
/* 147 */           this.ethernetAddressField.setEnabled(false);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     private JPanel getOccupantInfoHelper() {
/* 153 */       JPanel occupantInfo = new JPanel();
/* 154 */       occupantInfo.setLayout(new GridLayout(11, 2, 1, 1));
/* 155 */       occupantInfo.add(new JLabel("Occupant Details:")); occupantInfo.add(new JLabel(""));
/*     */ 
/* 157 */       occupantInfo.add(new JLabel("ID:"));
/* 158 */       this.IDField = new JTextField(15);
/* 159 */       this.IDField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 161 */           String ID = TableView.CheckInPanel.this.IDField.getText();
/* 162 */           Container container = TableView.CheckInPanel.this.IDField.getTopLevelAncestor();
/* 163 */           if (ID.matches("[a-zA-Z][0-9]{8}")) {
/* 164 */             TableView.CheckInPanel.this.command.setID(ID);
/* 165 */           } else if (!ID.equals("")) {
/* 166 */             TableView.CheckInPanel.this.IDField.setText(TableView.CheckInPanel.this.command.getID());
/* 167 */             JOptionPane.showMessageDialog(container, "The format of the inputted ID is invalid. It should be an English letter followed by exactly 8 digits", 
/* 168 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 172 */       occupantInfo.add(this.IDField);
/*     */ 
/* 174 */       occupantInfo.add(new JLabel("Name:"));
/* 175 */       this.nameField = new JTextField(15);
/* 176 */       this.nameField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 178 */           TableView.CheckInPanel.this.command.setName(TableView.CheckInPanel.this.nameField.getText());
/*     */         }
/*     */       });
/* 181 */       occupantInfo.add(this.nameField);
/*     */ 
/* 183 */       occupantInfo.add(new JLabel("Type:"));
/* 184 */       this.typeField = new JComboBox(new String[] { "Standard", "Business" });
/* 185 */       this.typeField.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 187 */           TableView.CheckInPanel.this.command.setType((String)TableView.CheckInPanel.this.typeField.getSelectedItem());
/* 188 */           TableView.CheckInPanel.this.updateDataServiceRequired();
/*     */         }
/*     */       });
/* 191 */       this.typeField.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 193 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 194 */             JFrame f = (JFrame)TableView.CheckInPanel.this.typeField.getTopLevelAncestor();
/* 195 */             if (f != null) {
/* 196 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 201 */       occupantInfo.add(this.typeField);
/*     */ 
/* 203 */       occupantInfo.add(new JLabel("Company:"));
/* 204 */       this.companyField = new JTextField(15);
/* 205 */       this.companyField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 207 */           TableView.CheckInPanel.this.command.setCompany(TableView.CheckInPanel.this.companyField.getText());
/*     */         }
/*     */       });
/* 210 */       occupantInfo.add(this.companyField);
/*     */ 
/* 212 */       occupantInfo.add(new JLabel(""));
/* 213 */       occupantInfo.add(new JLabel(""));
/* 214 */       occupantInfo.add(new JLabel("Occupation Details:"));
/* 215 */       occupantInfo.add(new JLabel(""));
/* 216 */       occupantInfo.add(new JLabel("Date of Check-in (dd-MM-yyyy):"));
/* 217 */       this.checkInDateField = new JTextField(15);
/* 218 */       this.checkInDateField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 220 */           String date = TableView.CheckInPanel.this.checkInDateField.getText();
/* 221 */           Container container = TableView.CheckInPanel.this.checkInDateField.getTopLevelAncestor();
/*     */           try {
/* 223 */             TableView.CheckInPanel.this.command.setCheckInDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
/*     */           }
/*     */           catch (Exception ex) {
/* 226 */             TableView.CheckInPanel.this.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(TableView.CheckInPanel.this.command.getCheckInDate()));
/* 227 */             JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
/* 228 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 232 */       occupantInfo.add(this.checkInDateField);
/*     */ 
/* 234 */       occupantInfo.add(new JLabel("Is Data Service Required?"));
/* 235 */       this.dataServiceRequiredBox = new JComboBox(new String[] { "Yes", "No" });
/* 236 */       this.dataServiceRequiredBox.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 238 */           if (TableView.CheckInPanel.this.dataServiceRequiredBox.getSelectedItem().equals("Yes"))
/* 239 */             TableView.CheckInPanel.this.command.setDataServiceRequired(true);
/*     */           else {
/* 241 */             TableView.CheckInPanel.this.command.setDataServiceRequired(false);
/*     */           }
/* 243 */           TableView.CheckInPanel.this.updateDataServiceRequired();
/*     */         }
/*     */       });
/* 246 */       this.dataServiceRequiredBox.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 248 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 249 */             JFrame f = (JFrame)TableView.CheckInPanel.this.dataServiceRequiredBox.getTopLevelAncestor();
/* 250 */             if (f != null) {
/* 251 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 256 */       occupantInfo.add(this.dataServiceRequiredBox);
/*     */ 
/* 258 */       occupantInfo.add(new JLabel("EthernetAddress:"));
/* 259 */       this.ethernetAddressField = new JTextField(15);
/* 260 */       this.ethernetAddressField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 262 */           String text = TableView.CheckInPanel.this.ethernetAddressField.getText();
/* 263 */           Container container = TableView.CheckInPanel.this.ethernetAddressField.getTopLevelAncestor();
/* 264 */           if (text.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}")) {
/* 265 */             TableView.CheckInPanel.this.command.setEthernetAddress(text);
/*     */           } else {
/* 267 */             JOptionPane.showMessageDialog(container, "The format of the inputted ethernet address is invalid", 
/* 268 */               "Input Error", 1);
/* 269 */             TableView.CheckInPanel.this.ethernetAddressField.setText(TableView.CheckInPanel.this.command.getEthernetAddress());
/*     */           }
/*     */         }
/*     */       });
/* 273 */       occupantInfo.add(this.ethernetAddressField);
/*     */ 
/* 275 */       return occupantInfo;
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 279 */       JPanel roomInfo = new JPanel();
/* 280 */       roomInfo.setLayout(new BorderLayout());
/* 281 */       roomInfo.add(new JLabel("Available Room List:"), "North");
/*     */ 
/* 283 */       this.availableRoomTable = new JTable();
/* 284 */       roomInfo.add(new JScrollPane(this.availableRoomTable), "Center");
/* 285 */       add(roomInfo, "Center");
/*     */ 
/* 287 */       this.availableRoomTable.setSelectionMode(0);
/* 288 */       final ListSelectionModel model = this.availableRoomTable.getSelectionModel();
/* 289 */       model.addListSelectionListener(new ListSelectionListener() {
/*     */         public void valueChanged(ListSelectionEvent e) {
/* 291 */           int index = model.getMinSelectionIndex();
/* 292 */           if (index != -1) {
/* 293 */             Room room = (Room)TableView.CheckInPanel.this.availableRooms[index];
/* 294 */             if (room != null) {
/* 295 */               TableView.CheckInPanel.this.command.setSelectedRoom(room);
/* 296 */               TableView.CheckInPanel.this.updateDataServiceRequired();
/*     */             }
/*     */           }
/*     */         } } );
/*     */     } } 
/*     */   private class CheckOutPanel extends JPanel {
/* 305 */     private CheckOutCommand command = (CheckOutCommand)TableView.this.currentCommand;
/*     */     private JTextField checkOutDateField;
/*     */     private JTable occupiedRoomTable;
/*     */     private Object[] occupiedRooms;
/*     */     private JButton checkOutButton;
/*     */ 
/* 312 */     public CheckOutPanel() { setLayout(new BorderLayout());
/* 313 */       addOccupantInfo();
/* 314 */       addRoomInfo();
/* 315 */       getCommandValues(); }
/*     */ 
/*     */     private void addOccupantInfo()
/*     */     {
/* 319 */       JPanel wholePanel = new JPanel();
/* 320 */       add(wholePanel, "North");
/* 321 */       wholePanel.setLayout(new GridLayout(1, 2, 1, 1));
/* 322 */       wholePanel.setBorder(new BevelBorder(1));
/*     */ 
/* 324 */       JPanel checkOutDatePanel = new JPanel();
/* 325 */       wholePanel.add(checkOutDatePanel);
/*     */ 
/* 327 */       checkOutDatePanel.add(new JLabel("Date of Check-out (dd-MM-yyyy):"));
/* 328 */       this.checkOutDateField = new JTextField(15);
/* 329 */       this.checkOutDateField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 331 */           String date = TableView.CheckOutPanel.this.checkOutDateField.getText();
/* 332 */           Container container = TableView.CheckOutPanel.this.checkOutDateField.getTopLevelAncestor();
/*     */           try {
/* 334 */             TableView.CheckOutPanel.this.command.setCheckOutDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
/*     */           }
/*     */           catch (Exception ex) {
/* 337 */             TableView.CheckOutPanel.this.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(TableView.CheckOutPanel.this.command.getCheckInDate()));
/* 338 */             JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
/* 339 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 343 */       checkOutDatePanel.add(this.checkOutDateField);
/*     */ 
/* 345 */       JToolBar checkOutToolbar = new JToolBar();
/* 346 */       wholePanel.add(checkOutToolbar);
/*     */ 
/* 348 */       this.checkOutButton = new JButton("Check Out");
/* 349 */       this.checkOutButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 351 */           Container container = TableView.CheckOutPanel.this.checkOutButton.getTopLevelAncestor();
/* 352 */           if (TableView.CheckOutPanel.this.command.getSelectedRoom() == null)
/*     */           {
/* 354 */             JOptionPane.showMessageDialog(container, "No room is selected", 
/* 355 */               "Input Error", 1);
/* 356 */             return;
/*     */           }
/* 358 */           String result = TableView.CheckOutPanel.this.command.checkOut();
/* 359 */           if (result.equals("Success"))
/* 360 */             TableView.CheckOutPanel.this.getCommandValues();
/*     */           else
/* 362 */             JOptionPane.showMessageDialog(container, result, 
/* 363 */               "Input Error", 1);
/*     */         }
/*     */       });
/* 367 */       checkOutToolbar.add(this.checkOutButton);
/*     */     }
/*     */ 
/*     */     private void getCommandValues() {
/* 371 */       ArrayList list = TableView.this.hotelManager.listAllOccupiedRooms();
/* 372 */       this.occupiedRooms = list.toArray();
/* 373 */       this.occupiedRoomTable.setModel(new AbstractTableModel() {
/* 374 */         String[] headers = { "Room No", "Room Type", "Capacity", "Rate", "Data Service", "Ethernet Address", 
/* 375 */           "Occupant Name", "Occupant Type", "Member ID", "Company", "Check-in Date" };
/*     */ 
/* 376 */         public int getRowCount() { return TableView.CheckOutPanel.this.occupiedRooms.length; } 
/* 377 */         public int getColumnCount() { return this.headers.length; } 
/*     */         public Object getValueAt(int r, int c) {
/* 379 */           Room room = (Room)TableView.CheckOutPanel.this.occupiedRooms[r];
/* 380 */           Occupation occupation = room.getOccupation();
/* 381 */           Occupant occupant = occupation.getOccupant();
/* 382 */           if (room == null) return null;
/* 383 */           switch (c) { case 0:
/* 384 */             return room.getFloorNo() + "-" + room.getRoomNo();
/*     */           case 1:
/* 385 */             return room.getTypeString();
/*     */           case 2:
/* 386 */             return new Integer(room.getCapacity());
/*     */           case 3:
/* 387 */             return new Double(room.getRate());
/*     */           case 4:
/* 388 */             if (room.getType() == 1) return "N/A";
/* 389 */             if (occupation.isDataServiceRequired()) return "In used";
/* 390 */             return "Not in used";
/*     */           case 5:
/* 391 */             return occupation.getEthernetAddress();
/*     */           case 6:
/* 392 */             return occupant.getName();
/*     */           case 7:
/* 393 */             return occupant.getType();
/*     */           case 8:
/* 394 */             return occupant.getID();
/*     */           case 9:
/* 395 */             return occupant.getCompany();
/*     */           case 10:
/* 396 */             return new SimpleDateFormat("dd-MM-yyyy").format(occupation.getCheckInDate());
/*     */           }
/* 398 */           return "";
/*     */         }
/* 400 */         public String getColumnName(int c) { return this.headers[c]; }
/*     */ 
/*     */       });
/* 402 */       Room selectedRoom = this.command.getSelectedRoom();
/* 403 */       if (selectedRoom != null) {
/* 404 */         int index = list.indexOf(selectedRoom);
/* 405 */         this.occupiedRoomTable.setRowSelectionInterval(index, index);
/*     */       }
/* 407 */       this.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(this.command.getCheckOutDate()));
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 411 */       JPanel roomInfo = new JPanel();
/* 412 */       roomInfo.setLayout(new BorderLayout());
/* 413 */       roomInfo.add(new JLabel(" Occupied Room List:"), "North");
/*     */ 
/* 415 */       this.occupiedRoomTable = new JTable();
/* 416 */       roomInfo.add(new JScrollPane(this.occupiedRoomTable), "Center");
/* 417 */       add(roomInfo, "Center");
/*     */ 
/* 419 */       this.occupiedRoomTable.setSelectionMode(0);
/*     */ 
/* 421 */       final ListSelectionModel model = this.occupiedRoomTable.getSelectionModel();
/* 422 */       model.addListSelectionListener(new ListSelectionListener() {
/*     */         public void valueChanged(ListSelectionEvent e) {
/* 424 */           int index = model.getMinSelectionIndex();
/* 425 */           if (index != -1) {
/* 426 */             Room room = (Room)TableView.CheckOutPanel.this.occupiedRooms[index];
/* 427 */             if (room != null) {
/* 428 */               TableView.CheckOutPanel.this.command.setSelectedRoom(room);
/* 429 */               Occupation occupation = room.getOccupation();
/* 430 */               Occupant occupant = occupation.getOccupant();
/* 431 */               TableView.CheckOutPanel.this.command.setID(occupant.getID());
/* 432 */               TableView.CheckOutPanel.this.command.setName(occupant.getName());
/* 433 */               TableView.CheckOutPanel.this.command.setType(occupant.getType());
/* 434 */               TableView.CheckOutPanel.this.command.setCompany(occupant.getCompany());
/* 435 */               TableView.CheckOutPanel.this.command.setCheckInDate(occupation.getCheckInDate());
/* 436 */               TableView.CheckOutPanel.this.command.setDataServiceRequired(occupation.isDataServiceRequired());
/* 437 */               TableView.CheckOutPanel.this.command.setEthernetAddress(occupation.getEthernetAddress());
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SearchPanel extends JPanel {
/* 446 */     private SearchCommand command = (SearchCommand)TableView.this.currentCommand;
/*     */     private JTextField searchField;
/*     */     private JComboBox typeField;
/*     */     private JButton searchButton;
/*     */     private JTable searchRoomTable;
/* 451 */     private Object[] searchResults = new Room[0];
/*     */ 
/*     */     public SearchPanel() {
/* 454 */       setLayout(new BorderLayout());
/* 455 */       addSearchInfo();
/* 456 */       addRoomInfo();
/* 457 */       getCommandValues();
/*     */     }
/*     */ 
/*     */     private void addSearchInfo() {
/* 461 */       JPanel wholePanel = new JPanel();
/* 462 */       add(wholePanel, "North");
/* 463 */       wholePanel.setLayout(new GridLayout(1, 2, 1, 1));
/* 464 */       wholePanel.setBorder(new BevelBorder(1));
/*     */ 
/* 466 */       JPanel searchInfo = new JPanel();
/* 467 */       JPanel innerPanel = new JPanel();
/* 468 */       innerPanel.add(searchInfo);
/* 469 */       wholePanel.add(innerPanel);
/* 470 */       searchInfo.setLayout(new GridLayout(2, 2, 1, 1));
/*     */ 
/* 472 */       searchInfo.add(new JLabel("Search Field:"));
/* 473 */       this.searchField = new JTextField(15);
/* 474 */       this.searchField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 476 */           TableView.SearchPanel.this.command.setSearchField(TableView.SearchPanel.this.searchField.getText());
/*     */         }
/*     */       });
/* 479 */       searchInfo.add(this.searchField);
/*     */ 
/* 481 */       searchInfo.add(new JLabel("Type:"));
/* 482 */       this.typeField = new JComboBox(new String[] { "ID", "Name", "Type", "Company" });
/* 483 */       this.typeField.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 485 */           TableView.SearchPanel.this.command.setType((String)TableView.SearchPanel.this.typeField.getSelectedItem());
/*     */         }
/*     */       });
/* 488 */       this.typeField.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 490 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 491 */             JFrame f = (JFrame)TableView.SearchPanel.this.typeField.getTopLevelAncestor();
/* 492 */             if (f != null) {
/* 493 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 498 */       searchInfo.add(this.typeField);
/*     */ 
/* 500 */       JToolBar searchToolbar = new JToolBar();
/* 501 */       wholePanel.add(searchToolbar);
/*     */ 
/* 503 */       this.searchButton = new JButton("Search");
/* 504 */       this.searchButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 506 */           ArrayList result = TableView.SearchPanel.this.command.search();
/* 507 */           TableView.SearchPanel.this.searchResults = result.toArray();
/* 508 */           TableView.SearchPanel.this.command.setSearchResults(result.toArray());
/* 509 */           TableView.SearchPanel.this.getCommandValues();
/*     */         }
/*     */       });
/* 512 */       searchToolbar.add(this.searchButton);
/*     */     }
/*     */ 
/*     */     private JPanel getSearchInfoHelper() {
/* 516 */       JPanel searchInfo = new JPanel();
/* 517 */       searchInfo.setLayout(new GridLayout(2, 2, 1, 1));
/*     */ 
/* 519 */       searchInfo.add(new JLabel("Search Field:"));
/* 520 */       this.searchField = new JTextField(15);
/* 521 */       this.searchField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 523 */           TableView.SearchPanel.this.command.setSearchField(TableView.SearchPanel.this.searchField.getText());
/*     */         }
/*     */       });
/* 526 */       searchInfo.add(this.searchField);
/*     */ 
/* 528 */       searchInfo.add(new JLabel("Type:"));
/* 529 */       this.typeField = new JComboBox(new String[] { "ID", "Name", "Type", "Company" });
/* 530 */       this.typeField.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 532 */           TableView.SearchPanel.this.command.setType((String)TableView.SearchPanel.this.typeField.getSelectedItem());
/*     */         }
/*     */       });
/* 535 */       this.typeField.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 537 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 538 */             JFrame f = (JFrame)TableView.SearchPanel.this.typeField.getTopLevelAncestor();
/* 539 */             if (f != null) {
/* 540 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 545 */       searchInfo.add(this.typeField);
/*     */ 
/* 547 */       return searchInfo;
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 551 */       JPanel roomInfo = new JPanel();
/* 552 */       roomInfo.setLayout(new BorderLayout());
/* 553 */       roomInfo.add(new JLabel("Searched Room List:"), "North");
/*     */ 
/* 555 */       this.searchRoomTable = new JTable();
/* 556 */       roomInfo.add(new JScrollPane(this.searchRoomTable), "Center");
/* 557 */       add(roomInfo, "Center");
/*     */     }
/*     */ 
/*     */     private void getCommandValues() {
/* 561 */       this.searchField.setText(this.command.getSearchField());
/* 562 */       this.typeField.setSelectedItem(this.command.getType());
/* 563 */       Object[] results = this.command.getSearchResults();
/* 564 */       if (results != null) {
/* 565 */         this.searchResults = results;
/*     */       }
/*     */ 
/* 568 */       this.searchRoomTable.setModel(new AbstractTableModel() {
/* 569 */         String[] headers = { "Room No", "Room Type", "Capacity", "Rate", "Data Service", "Ethernet Address", 
/* 570 */           "Occupant Name", "Occupant Type", "Member ID", "Company", "Check-in Date" };
/*     */ 
/* 571 */         public int getRowCount() { return TableView.SearchPanel.this.searchResults.length; } 
/* 572 */         public int getColumnCount() { return this.headers.length; } 
/*     */         public Object getValueAt(int r, int c) {
/* 574 */           Room room = (Room)TableView.SearchPanel.this.searchResults[r];
/* 575 */           Occupation occupation = room.getOccupation();
/* 576 */           Occupant occupant = occupation.getOccupant();
/* 577 */           if (room == null) return null;
/* 578 */           switch (c) { case 0:
/* 579 */             return room.getFloorNo() + "-" + room.getRoomNo();
/*     */           case 1:
/* 580 */             return room.getTypeString();
/*     */           case 2:
/* 581 */             return new Integer(room.getCapacity());
/*     */           case 3:
/* 582 */             return new Double(room.getRate());
/*     */           case 4:
/* 583 */             if (room.getType() == 1) return "N/A";
/* 584 */             if (occupation.isDataServiceRequired()) return "In used";
/* 585 */             return "Not in used";
/*     */           case 5:
/* 586 */             return occupation.getEthernetAddress();
/*     */           case 6:
/* 587 */             return occupant.getName();
/*     */           case 7:
/* 588 */             return occupant.getType();
/*     */           case 8:
/* 589 */             return occupant.getID();
/*     */           case 9:
/* 590 */             return occupant.getCompany();
/*     */           case 10:
/* 591 */             return new SimpleDateFormat("dd-MM-yyyy").format(occupation.getCheckInDate());
/*     */           }
/* 593 */           return "";
/*     */         }
/* 595 */         public String getColumnName(int c) { return this.headers[c]; }
/*     */ 
/*     */       });
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     TableView
 * JD-Core Version:    0.6.2
 */