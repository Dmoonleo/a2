/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
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
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class ListView
/*     */   implements View
/*     */ {
/*     */   private HotelManager hotelManager;
/*     */   private Command currentCommand;
/*     */ 
/*     */   public ListView(HotelManager hotelManager)
/*     */   {
/*  14 */     this.hotelManager = hotelManager;
/*     */   }
/*     */ 
/*     */   public JPanel accept(Command command) {
/*  18 */     this.currentCommand = command;
/*  19 */     return command.getPanel(this);
/*     */   }
/*     */ 
/*     */   public JPanel checkInPanel() {
/*  23 */     return new ListView.CheckInPanel();
/*     */   }
/*     */ 
/*     */   public JPanel checkOutPanel() {
/*  27 */     return new ListView.CheckOutPanel();
/*     */   }
/*     */ 
/*     */   public JPanel searchPanel() {
/*  31 */     return new ListView.SearchPanel(); } 
/*  35 */   private class CheckInPanel extends JPanel { private CheckInCommand command = (CheckInCommand)ListView.this.currentCommand;
/*     */     private JTextField IDField;
/*     */     private JTextField nameField;
/*     */     private JComboBox typeField;
/*     */     private JTextField companyField;
/*     */     private JTextField checkInDateField;
/*     */     private JComboBox dataServiceRequiredBox;
/*     */     private JTextField ethernetAddressField;
/*     */     private JList availableRoomList;
/*     */     private JButton checkInButton;
/*     */ 
/*  47 */     public CheckInPanel() { setLayout(new BorderLayout());
/*  48 */       addOccupantInfo();
/*  49 */       addRoomInfo();
/*  50 */       getCommandValues();
/*  51 */       updateDataServiceRequired(); }
/*     */ 
/*     */     private void addOccupantInfo()
/*     */     {
/*  55 */       JPanel wholePanel = new JPanel();
/*  56 */       JPanel upperPanel = new JPanel();
/*  57 */       JPanel lowerPanel = new JPanel();
/*  58 */       wholePanel.setLayout(new BorderLayout());
/*  59 */       lowerPanel.setLayout(new BorderLayout());
/*     */ 
/*  61 */       add(wholePanel, "West");
/*  62 */       wholePanel.add(upperPanel, "Center");
/*  63 */       wholePanel.add(lowerPanel, "South");
/*  64 */       upperPanel.add(getOccupantInfoHelper());
/*  65 */       this.checkInButton = new JButton("Check in");
/*  66 */       this.checkInButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/*  68 */           Container container = ListView.CheckInPanel.this.checkInButton.getTopLevelAncestor();
/*  69 */           if (ListView.CheckInPanel.this.command.getSelectedRoom() == null)
/*     */           {
/*  71 */             JOptionPane.showMessageDialog(container, "No room is selected", 
/*  72 */               "Input Error", 1);
/*  73 */             return;
/*     */           }
/*  75 */           String result = ListView.CheckInPanel.this.command.checkIn();
/*  76 */           if (result.equals("Success"))
/*  77 */             ListView.CheckInPanel.this.getCommandValues();
/*     */           else
/*  79 */             JOptionPane.showMessageDialog(container, result, 
/*  80 */               "Input Error", 1);
/*     */         }
/*     */       });
/*  84 */       lowerPanel.add(this.checkInButton);
/*     */     }
/*     */ 
/*     */     private void getCommandValues() {
/*  88 */       ArrayList list = ListView.this.hotelManager.listAllAvailableRooms();
/*  89 */       this.availableRoomList.setListData(list.toArray());
/*  90 */       Room selectedRoom = this.command.getSelectedRoom();
/*  91 */       if (selectedRoom != null) {
/*  92 */         this.availableRoomList.setSelectedValue(this.command.getSelectedRoom(), true);
/*     */       }
/*  94 */       this.IDField.setText(this.command.getID());
/*  95 */       this.nameField.setText(this.command.getName());
/*  96 */       this.typeField.setSelectedItem(this.command.getType());
/*  97 */       this.companyField.setText(this.command.getCompany());
/*  98 */       this.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(this.command.getCheckInDate()));
/*  99 */       updateDataServiceRequired();
/* 100 */       this.ethernetAddressField.setText(this.command.getEthernetAddress());
/*     */     }
/*     */ 
/*     */     private void updateDataServiceRequired() {
/* 104 */       Room room = this.command.getSelectedRoom();
/* 105 */       if ((room == null) || (room.getType() == 1) || (this.typeField.getSelectedItem().equals("Standard"))) {
/* 106 */         this.dataServiceRequiredBox.setEnabled(false);
/* 107 */         this.ethernetAddressField.setEnabled(false);
/* 108 */         if (this.dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
/* 109 */           this.dataServiceRequiredBox.setSelectedItem("No");
/*     */         }
/* 111 */         this.command.setDataServiceRequired(false);
/*     */       } else {
/* 113 */         this.dataServiceRequiredBox.setEnabled(true);
/* 114 */         if (this.command.isDataServiceRequired()) {
/* 115 */           if (this.dataServiceRequiredBox.getSelectedItem().equals("No")) {
/* 116 */             this.dataServiceRequiredBox.setSelectedItem("Yes");
/*     */           }
/* 118 */           this.ethernetAddressField.setEnabled(true);
/*     */         } else {
/* 120 */           if (this.dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
/* 121 */             this.dataServiceRequiredBox.setSelectedItem("No");
/*     */           }
/* 123 */           this.ethernetAddressField.setEnabled(false);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     private JPanel getOccupantInfoHelper() {
/* 129 */       JPanel occupantInfo = new JPanel();
/* 130 */       occupantInfo.setLayout(new GridLayout(11, 2, 1, 1));
/* 131 */       occupantInfo.add(new JLabel("Occupant Details:")); occupantInfo.add(new JLabel(""));
/*     */ 
/* 133 */       occupantInfo.add(new JLabel("ID:"));
/* 134 */       this.IDField = new JTextField(15);
/* 135 */       this.IDField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 137 */           String ID = ListView.CheckInPanel.this.IDField.getText();
/* 138 */           Container container = ListView.CheckInPanel.this.IDField.getTopLevelAncestor();
/* 139 */           if (ID.matches("[a-zA-Z][0-9]{8}")) {
/* 140 */             ListView.CheckInPanel.this.command.setID(ID);
/* 141 */           } else if (!ID.equals("")) {
/* 142 */             ListView.CheckInPanel.this.IDField.setText(ListView.CheckInPanel.this.command.getID());
/* 143 */             JOptionPane.showMessageDialog(container, "The format of the inputted ID is invalid. It should be an English letter followed by exactly 8 digits", 
/* 144 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 148 */       occupantInfo.add(this.IDField);
/*     */ 
/* 150 */       occupantInfo.add(new JLabel("Name:"));
/* 151 */       this.nameField = new JTextField(15);
/* 152 */       this.nameField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 154 */           ListView.CheckInPanel.this.command.setName(ListView.CheckInPanel.this.nameField.getText());
/*     */         }
/*     */       });
/* 157 */       occupantInfo.add(this.nameField);
/*     */ 
/* 159 */       occupantInfo.add(new JLabel("Type:"));
/* 160 */       this.typeField = new JComboBox(new String[] { "Standard", "Business" });
/* 161 */       this.typeField.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 163 */           ListView.CheckInPanel.this.command.setType((String)ListView.CheckInPanel.this.typeField.getSelectedItem());
/* 164 */           ListView.CheckInPanel.this.updateDataServiceRequired();
/*     */         }
/*     */       });
/* 167 */       this.typeField.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 169 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 170 */             JFrame f = (JFrame)ListView.CheckInPanel.this.typeField.getTopLevelAncestor();
/* 171 */             if (f != null) {
/* 172 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 177 */       occupantInfo.add(this.typeField);
/*     */ 
/* 179 */       occupantInfo.add(new JLabel("Company:"));
/* 180 */       this.companyField = new JTextField(15);
/* 181 */       this.companyField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 183 */           ListView.CheckInPanel.this.command.setCompany(ListView.CheckInPanel.this.companyField.getText());
/*     */         }
/*     */       });
/* 186 */       occupantInfo.add(this.companyField);
/*     */ 
/* 188 */       occupantInfo.add(new JLabel(""));
/* 189 */       occupantInfo.add(new JLabel(""));
/* 190 */       occupantInfo.add(new JLabel("Occupation Details:"));
/* 191 */       occupantInfo.add(new JLabel(""));
/* 192 */       occupantInfo.add(new JLabel("Date of Check-in (dd-MM-yyyy):"));
/* 193 */       this.checkInDateField = new JTextField(15);
/* 194 */       this.checkInDateField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 196 */           String date = ListView.CheckInPanel.this.checkInDateField.getText();
/* 197 */           Container container = ListView.CheckInPanel.this.checkInDateField.getTopLevelAncestor();
/*     */           try {
/* 199 */             ListView.CheckInPanel.this.command.setCheckInDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
/*     */           }
/*     */           catch (Exception ex) {
/* 202 */             ListView.CheckInPanel.this.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(ListView.CheckInPanel.this.command.getCheckInDate()));
/* 203 */             JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
/* 204 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 208 */       occupantInfo.add(this.checkInDateField);
/*     */ 
/* 210 */       occupantInfo.add(new JLabel("Is Data Service Required?"));
/* 211 */       this.dataServiceRequiredBox = new JComboBox(new String[] { "Yes", "No" });
/* 212 */       this.dataServiceRequiredBox.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 214 */           if (ListView.CheckInPanel.this.dataServiceRequiredBox.getSelectedItem().equals("Yes"))
/* 215 */             ListView.CheckInPanel.this.command.setDataServiceRequired(true);
/*     */           else {
/* 217 */             ListView.CheckInPanel.this.command.setDataServiceRequired(false);
/*     */           }
/* 219 */           ListView.CheckInPanel.this.updateDataServiceRequired();
/*     */         }
/*     */       });
/* 222 */       this.dataServiceRequiredBox.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 224 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 225 */             JFrame f = (JFrame)ListView.CheckInPanel.this.dataServiceRequiredBox.getTopLevelAncestor();
/* 226 */             if (f != null) {
/* 227 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 232 */       occupantInfo.add(this.dataServiceRequiredBox);
/*     */ 
/* 234 */       occupantInfo.add(new JLabel("EthernetAddress:"));
/* 235 */       this.ethernetAddressField = new JTextField(15);
/* 236 */       this.ethernetAddressField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 238 */           String text = ListView.CheckInPanel.this.ethernetAddressField.getText();
/* 239 */           Container container = ListView.CheckInPanel.this.ethernetAddressField.getTopLevelAncestor();
/* 240 */           if (text.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}")) {
/* 241 */             ListView.CheckInPanel.this.command.setEthernetAddress(text);
/*     */           } else {
/* 243 */             JOptionPane.showMessageDialog(container, "The format of the inputted ethernet address is invalid", 
/* 244 */               "Input Error", 1);
/* 245 */             ListView.CheckInPanel.this.ethernetAddressField.setText(ListView.CheckInPanel.this.command.getEthernetAddress());
/*     */           }
/*     */         }
/*     */       });
/* 249 */       occupantInfo.add(this.ethernetAddressField);
/*     */ 
/* 251 */       return occupantInfo;
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 255 */       JPanel roomInfo = new JPanel();
/* 256 */       roomInfo.setLayout(new BorderLayout());
/* 257 */       roomInfo.add(new JLabel("Available Room List:"), "North");
/*     */ 
/* 259 */       this.availableRoomList = new JList();
/* 260 */       roomInfo.add(new JScrollPane(this.availableRoomList), "Center");
/* 261 */       add(roomInfo, "Center");
/*     */ 
/* 263 */       this.availableRoomList.setSelectionMode(0);
/* 264 */       this.availableRoomList.addListSelectionListener(new ListSelectionListener() {
/*     */         public void valueChanged(ListSelectionEvent e) {
/* 266 */           Room room = (Room)ListView.CheckInPanel.this.availableRoomList.getSelectedValue();
/* 267 */           if (room != null) {
/* 268 */             ListView.CheckInPanel.this.command.setSelectedRoom(room);
/* 269 */             ListView.CheckInPanel.this.updateDataServiceRequired(); }  }  } ); }  } 
/* 277 */   private class CheckOutPanel extends JPanel { private CheckOutCommand command = (CheckOutCommand)ListView.this.currentCommand;
/*     */     private JTextField IDField;
/*     */     private JTextField nameField;
/*     */     private JComboBox typeField;
/*     */     private JTextField companyField;
/*     */     private JTextField checkInDateField;
/*     */     private JTextField checkOutDateField;
/*     */     private JComboBox dataServiceRequiredBox;
/*     */     private JTextField ethernetAddressField;
/*     */     private JList occupiedRoomList;
/*     */     private JButton checkOutButton;
/*     */ 
/* 290 */     public CheckOutPanel() { setLayout(new BorderLayout());
/* 291 */       addOccupantInfo();
/* 292 */       addRoomInfo();
/* 293 */       getCommandValues(true); }
/*     */ 
/*     */     private void addOccupantInfo()
/*     */     {
/* 297 */       JPanel wholePanel = new JPanel();
/* 298 */       JPanel upperPanel = new JPanel();
/* 299 */       JPanel lowerPanel = new JPanel();
/* 300 */       wholePanel.setLayout(new BorderLayout());
/* 301 */       lowerPanel.setLayout(new BorderLayout());
/*     */ 
/* 303 */       add(wholePanel, "West");
/* 304 */       wholePanel.add(upperPanel, "Center");
/* 305 */       wholePanel.add(lowerPanel, "South");
/* 306 */       upperPanel.add(getOccupantInfoHelper());
/* 307 */       this.checkOutButton = new JButton("Check Out");
/* 308 */       this.checkOutButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 310 */           Container container = ListView.CheckOutPanel.this.checkOutButton.getTopLevelAncestor();
/* 311 */           if (ListView.CheckOutPanel.this.command.getSelectedRoom() == null)
/*     */           {
/* 313 */             JOptionPane.showMessageDialog(container, "No room is selected", 
/* 314 */               "Input Error", 1);
/* 315 */             return;
/*     */           }
/* 317 */           String result = ListView.CheckOutPanel.this.command.checkOut();
/* 318 */           if (result.equals("Success"))
/* 319 */             ListView.CheckOutPanel.this.getCommandValues(true);
/*     */           else
/* 321 */             JOptionPane.showMessageDialog(container, result, 
/* 322 */               "Input Error", 1);
/*     */         }
/*     */       });
/* 326 */       lowerPanel.add(this.checkOutButton);
/*     */     }
/*     */ 
/*     */     private void getCommandValues(boolean setSelection) {
/* 330 */       ArrayList list = ListView.this.hotelManager.listAllOccupiedRooms();
/*     */ 
/* 332 */       Room selectedRoom = this.command.getSelectedRoom();
/*     */ 
/* 334 */       if (setSelection) {
/* 335 */         this.occupiedRoomList.setListData(list.toArray());
/*     */       }
/* 337 */       if ((selectedRoom != null) && (setSelection)) {
/* 338 */         this.occupiedRoomList.setSelectedValue(this.command.getSelectedRoom(), true);
/*     */       }
/* 340 */       this.IDField.setText(this.command.getID());
/* 341 */       this.nameField.setText(this.command.getName());
/* 342 */       this.typeField.setSelectedItem(this.command.getType());
/* 343 */       this.companyField.setText(this.command.getCompany());
/* 344 */       this.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(this.command.getCheckInDate()));
/* 345 */       this.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(this.command.getCheckOutDate()));
/* 346 */       if (this.command.isDataServiceRequired())
/* 347 */         this.dataServiceRequiredBox.setSelectedItem("Yes");
/*     */       else {
/* 349 */         this.dataServiceRequiredBox.setSelectedItem("No");
/*     */       }
/* 351 */       this.ethernetAddressField.setText(this.command.getEthernetAddress());
/*     */     }
/*     */ 
/*     */     private JPanel getOccupantInfoHelper() {
/* 355 */       JPanel occupantInfo = new JPanel();
/* 356 */       occupantInfo.setLayout(new GridLayout(12, 2, 1, 1));
/* 357 */       occupantInfo.add(new JLabel("Occupant Details:")); occupantInfo.add(new JLabel(""));
/*     */ 
/* 359 */       occupantInfo.add(new JLabel("ID:"));
/* 360 */       this.IDField = new JTextField(15);
/* 361 */       this.IDField.setEnabled(false);
/* 362 */       occupantInfo.add(this.IDField);
/*     */ 
/* 364 */       occupantInfo.add(new JLabel("Name:"));
/* 365 */       this.nameField = new JTextField(15);
/* 366 */       this.nameField.setEnabled(false);
/* 367 */       occupantInfo.add(this.nameField);
/*     */ 
/* 369 */       occupantInfo.add(new JLabel("Type:"));
/* 370 */       this.typeField = new JComboBox(new String[] { "Standard", "Business" });
/* 371 */       this.typeField.setEnabled(false);
/* 372 */       occupantInfo.add(this.typeField);
/*     */ 
/* 374 */       occupantInfo.add(new JLabel("Company:"));
/* 375 */       this.companyField = new JTextField(15);
/* 376 */       this.companyField.setEnabled(false);
/* 377 */       occupantInfo.add(this.companyField);
/*     */ 
/* 379 */       occupantInfo.add(new JLabel(""));
/* 380 */       occupantInfo.add(new JLabel(""));
/* 381 */       occupantInfo.add(new JLabel("Occupation Details:"));
/* 382 */       occupantInfo.add(new JLabel(""));
/* 383 */       occupantInfo.add(new JLabel("Date of Check-in (dd-MM-yyyy):"));
/* 384 */       this.checkInDateField = new JTextField(15);
/* 385 */       this.checkInDateField.setEnabled(false);
/* 386 */       occupantInfo.add(this.checkInDateField);
/*     */ 
/* 388 */       occupantInfo.add(new JLabel("Date of Check-out (dd-MM-yyyy):"));
/* 389 */       this.checkOutDateField = new JTextField(15);
/* 390 */       this.checkOutDateField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 392 */           String date = ListView.CheckOutPanel.this.checkOutDateField.getText();
/* 393 */           Container container = ListView.CheckOutPanel.this.checkOutDateField.getTopLevelAncestor();
/*     */           try {
/* 395 */             ListView.CheckOutPanel.this.command.setCheckOutDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
/*     */           }
/*     */           catch (Exception ex) {
/* 398 */             ListView.CheckOutPanel.this.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(ListView.CheckOutPanel.this.command.getCheckInDate()));
/* 399 */             JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
/* 400 */               "Input Error", 1);
/*     */           }
/*     */         }
/*     */       });
/* 404 */       occupantInfo.add(this.checkOutDateField);
/*     */ 
/* 406 */       occupantInfo.add(new JLabel("Is Data Service Required?"));
/* 407 */       this.dataServiceRequiredBox = new JComboBox(new String[] { "Yes", "No" });
/* 408 */       this.dataServiceRequiredBox.setEnabled(false);
/* 409 */       occupantInfo.add(this.dataServiceRequiredBox);
/*     */ 
/* 411 */       occupantInfo.add(new JLabel("EthernetAddress:"));
/* 412 */       this.ethernetAddressField = new JTextField(15);
/* 413 */       this.ethernetAddressField.setEnabled(false);
/* 414 */       occupantInfo.add(this.ethernetAddressField);
/*     */ 
/* 416 */       return occupantInfo;
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 420 */       JPanel roomInfo = new JPanel();
/* 421 */       roomInfo.setLayout(new BorderLayout());
/* 422 */       roomInfo.add(new JLabel("Occupied Room List:"), "North");
/*     */ 
/* 424 */       this.occupiedRoomList = new JList();
/* 425 */       roomInfo.add(new JScrollPane(this.occupiedRoomList), "Center");
/* 426 */       add(roomInfo, "Center");
/*     */ 
/* 428 */       this.occupiedRoomList.setSelectionMode(0);
/* 429 */       this.occupiedRoomList.setSelectionBackground(Color.lightGray);
/* 430 */       this.occupiedRoomList.addListSelectionListener(new ListSelectionListener() {
/*     */         public void valueChanged(ListSelectionEvent e) {
/* 432 */           Room room = (Room)ListView.CheckOutPanel.this.occupiedRoomList.getSelectedValue();
/* 433 */           if (room != null) {
/* 434 */             ListView.CheckOutPanel.this.command.setSelectedRoom(room);
/* 435 */             Occupation occupation = room.getOccupation();
/* 436 */             Occupant occupant = occupation.getOccupant();
/* 437 */             ListView.CheckOutPanel.this.command.setID(occupant.getID());
/* 438 */             ListView.CheckOutPanel.this.command.setName(occupant.getName());
/* 439 */             ListView.CheckOutPanel.this.command.setType(occupant.getType());
/* 440 */             ListView.CheckOutPanel.this.command.setCompany(occupant.getCompany());
/* 441 */             ListView.CheckOutPanel.this.command.setCheckInDate(occupation.getCheckInDate());
/* 442 */             ListView.CheckOutPanel.this.command.setDataServiceRequired(occupation.isDataServiceRequired());
/* 443 */             ListView.CheckOutPanel.this.command.setEthernetAddress(occupation.getEthernetAddress());
/* 444 */             ListView.CheckOutPanel.this.getCommandValues(false);
/*     */           }
/*     */         } } );
/*     */     } } 
/*     */   private class SearchPanel extends JPanel {
/* 453 */     private SearchCommand command = (SearchCommand)ListView.this.currentCommand;
/*     */     private JTextField searchField;
/*     */     private JComboBox typeField;
/*     */     private JButton searchButton;
/*     */     private JList searchRoomList;
/*     */ 
/*     */     public SearchPanel() {
/* 460 */       setLayout(new BorderLayout());
/* 461 */       addSearchInfo();
/* 462 */       addRoomInfo();
/* 463 */       getCommandValues();
/*     */     }
/*     */ 
/*     */     private void addSearchInfo() {
/* 467 */       JPanel wholePanel = new JPanel();
/* 468 */       JPanel upperPanel = new JPanel();
/* 469 */       JPanel lowerPanel = new JPanel();
/*     */ 
/* 471 */       wholePanel.setLayout(new BorderLayout());
/* 472 */       lowerPanel.setLayout(new BorderLayout());
/*     */ 
/* 474 */       add(wholePanel, "West");
/* 475 */       wholePanel.add(upperPanel, "Center");
/* 476 */       wholePanel.add(lowerPanel, "South");
/* 477 */       upperPanel.add(getSearchInfoHelper());
/* 478 */       this.searchButton = new JButton("Search");
/* 479 */       this.searchButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 481 */           ArrayList result = ListView.SearchPanel.this.command.search();
/* 482 */           ListView.SearchPanel.this.searchRoomList.setListData(result.toArray());
/* 483 */           ListView.SearchPanel.this.command.setSearchResults(result.toArray());
/*     */         }
/*     */       });
/* 486 */       lowerPanel.add(this.searchButton);
/*     */     }
/*     */ 
/*     */     private JPanel getSearchInfoHelper() {
/* 490 */       JPanel searchInfo = new JPanel();
/* 491 */       searchInfo.setLayout(new GridLayout(3, 2, 1, 1));
/* 492 */       searchInfo.add(new JLabel("Search Details:")); searchInfo.add(new JLabel(""));
/*     */ 
/* 494 */       searchInfo.add(new JLabel("Search Field:"));
/* 495 */       this.searchField = new JTextField(15);
/* 496 */       this.searchField.addFocusListener(new FocusAdapter() {
/*     */         public void focusLost(FocusEvent e) {
/* 498 */           ListView.SearchPanel.this.command.setSearchField(ListView.SearchPanel.this.searchField.getText());
/*     */         }
/*     */       });
/* 501 */       searchInfo.add(this.searchField);
/*     */ 
/* 503 */       searchInfo.add(new JLabel("Type:"));
/* 504 */       this.typeField = new JComboBox(new String[] { "ID", "Name", "Type", "Company" });
/* 505 */       this.typeField.addItemListener(new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent e) {
/* 507 */           ListView.SearchPanel.this.command.setType((String)ListView.SearchPanel.this.typeField.getSelectedItem());
/*     */         }
/*     */       });
/* 510 */       this.typeField.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 512 */           if (UIManager.getLookAndFeel().getName().equals("Windows")) {
/* 513 */             JFrame f = (JFrame)ListView.SearchPanel.this.typeField.getTopLevelAncestor();
/* 514 */             if (f != null) {
/* 515 */               f.setVisible(false); f.setVisible(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 520 */       searchInfo.add(this.typeField);
/*     */ 
/* 522 */       return searchInfo;
/*     */     }
/*     */ 
/*     */     private void addRoomInfo() {
/* 526 */       JPanel roomInfo = new JPanel();
/* 527 */       roomInfo.setLayout(new BorderLayout());
/* 528 */       roomInfo.add(new JLabel("Searched Room List:"), "North");
/*     */ 
/* 530 */       this.searchRoomList = new JList();
/* 531 */       roomInfo.add(new JScrollPane(this.searchRoomList), "Center");
/* 532 */       add(roomInfo, "Center");
/*     */     }
/*     */ 
/*     */     private void getCommandValues() {
/* 536 */       this.searchField.setText(this.command.getSearchField());
/* 537 */       this.typeField.setSelectedItem(this.command.getType());
/* 538 */       Object[] searchResults = this.command.getSearchResults();
/* 539 */       if (searchResults != null)
/* 540 */         this.searchRoomList.setListData(searchResults);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     ListView
 * JD-Core Version:    0.6.2
 */