/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.BevelBorder;
/*     */ 
/*     */ public class UI extends JFrame
/*     */ {
/*     */   private HotelManager hotelManager;
/*     */   private JMenuBar menuBar;
/*     */   private JPanel toolBarPanel;
/*     */   private JCheckBox listViewCheckBox;
/*     */   private JCheckBox tableViewCheckBox;
/*     */   private JCheckBox checkIn;
/*     */   private JCheckBox checkOut;
/*     */   private JCheckBox search;
/*     */   private View currentView;
/*     */   private Command currentCommand;
/*     */ 
/*     */   public UI(HotelManager hotelManager)
/*     */   {
/*  20 */     super("Hotel Management System");
/*     */ 
/*  22 */     this.hotelManager = hotelManager;
/*     */ 
/*  25 */     constructMenu();
/*     */ 
/*  28 */     constructToolBar();
/*     */ 
/*  31 */     addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent e) {
/*  33 */         System.exit(0);
/*     */       }
/*     */     });
/*  38 */     initializeViewAndCommand();
/*     */ 
/*  40 */     pack();
/*  41 */     setExtendedState(6);
/*  42 */     show();
/*     */   }
/*     */ 
/*     */   private void initializeViewAndCommand() {
/*  46 */     this.listViewCheckBox.setSelected(true);
/*  47 */     this.currentView = new ListView(this.hotelManager);
/*     */ 
/*  49 */     this.checkIn.setSelected(true);
/*  50 */     this.currentCommand = new CheckInCommand(this.hotelManager);
/*     */ 
/*  52 */     updateMainPanel();
/*     */   }
/*     */ 
/*     */   private void updateMainPanel() {
/*  56 */     JPanel panel = new JPanel();
/*  57 */     panel.setLayout(new BorderLayout());
/*  58 */     panel.add(this.toolBarPanel, "North");
/*  59 */     panel.add(this.currentView.accept(this.currentCommand), "Center");
/*  60 */     setContentPane(panel);
/*  61 */     invalidate();
/*  62 */     validate();
/*  63 */     setExtendedState(6);
/*     */   }
/*     */ 
/*     */   private void constructMenu() {
/*  67 */     this.menuBar = new JMenuBar();
/*  68 */     this.menuBar.setBorder(new BevelBorder(0));
/*     */ 
/*  70 */     JMenu fileMenu = new JMenu("File");
/*  71 */     this.menuBar.add(fileMenu);
/*  72 */     fileMenu.add(new AbstractAction("Exit", null) {
/*     */       public void actionPerformed(ActionEvent e) {
/*  74 */         System.exit(0);
/*     */       }
/*     */     });
/*  78 */     JMenu lookAndFeelMenu = new JMenu("Look and Feel");
/*  79 */     this.menuBar.add(lookAndFeelMenu);
/*     */ 
/*  81 */     lookAndFeelMenu.add(new AbstractAction("Metal", null) {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/*  84 */           UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
/*  85 */           SwingUtilities.updateComponentTreeUI(UI.this);
/*  86 */           UI.this.setVisible(false); UI.this.setVisible(true);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/*  91 */     lookAndFeelMenu.add(new AbstractAction("Motif", null) {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/*  94 */           UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
/*  95 */           SwingUtilities.updateComponentTreeUI(UI.this);
/*  96 */           UI.this.setVisible(false); UI.this.setVisible(true);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 101 */     lookAndFeelMenu.add(new AbstractAction("Windows", null) {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 104 */           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
/* 105 */           SwingUtilities.updateComponentTreeUI(UI.this);
/* 106 */           UI.this.setVisible(false); UI.this.setVisible(true);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 112 */     setJMenuBar(this.menuBar);
/*     */   }
/*     */ 
/*     */   private void constructToolBar() {
/* 116 */     this.toolBarPanel = new JPanel();
/* 117 */     getContentPane().add(this.toolBarPanel, "North");
/* 118 */     this.toolBarPanel.setLayout(new GridLayout(1, 2, 0, 0));
/*     */ 
/* 121 */     JToolBar viewToolbar = new JToolBar();
/* 122 */     this.toolBarPanel.add(viewToolbar);
/* 123 */     this.listViewCheckBox = new JCheckBox("ListView");
/* 124 */     this.listViewCheckBox.setSelected(true);
/* 125 */     viewToolbar.add(this.listViewCheckBox);
/* 126 */     this.tableViewCheckBox = new JCheckBox("Table View");
/* 127 */     viewToolbar.add(this.tableViewCheckBox);
/* 128 */     ButtonGroup viewGroup = new ButtonGroup();
/* 129 */     viewGroup.add(this.tableViewCheckBox);
/* 130 */     viewGroup.add(this.listViewCheckBox);
/* 131 */     viewToolbar.setFloatable(false);
/*     */ 
/* 134 */     addViewListeners();
/*     */ 
/* 137 */     JToolBar commandToolbar = new JToolBar();
/* 138 */     this.toolBarPanel.add(commandToolbar);
/* 139 */     this.checkIn = new JCheckBox("Check In");
/* 140 */     commandToolbar.add(this.checkIn);
/* 141 */     this.checkIn.setSelected(true);
/* 142 */     this.checkOut = new JCheckBox("Check Out");
/* 143 */     commandToolbar.add(this.checkOut);
/* 144 */     this.search = new JCheckBox("Search");
/* 145 */     commandToolbar.add(this.search);
/* 146 */     ButtonGroup commandGroup = new ButtonGroup();
/* 147 */     commandGroup.add(this.checkIn);
/* 148 */     commandGroup.add(this.checkOut);
/* 149 */     commandGroup.add(this.search);
/* 150 */     commandToolbar.setFloatable(false);
/*     */ 
/* 153 */     addCommandListeners();
/*     */   }
/*     */ 
/*     */   private void addViewListeners() {
/* 157 */     this.listViewCheckBox.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 159 */         UI.this.currentView = new ListView(UI.this.hotelManager);
/* 160 */         UI.this.updateMainPanel();
/*     */       }
/*     */     });
/* 163 */     this.tableViewCheckBox.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 165 */         UI.this.currentView = new TableView(UI.this.hotelManager);
/* 166 */         UI.this.updateMainPanel();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void addCommandListeners() {
/* 172 */     this.checkIn.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 174 */         UI.this.currentCommand = new CheckInCommand(UI.this.hotelManager);
/* 175 */         UI.this.updateMainPanel();
/*     */       }
/*     */     });
/* 178 */     this.checkOut.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 180 */         UI.this.currentCommand = new CheckOutCommand(UI.this.hotelManager);
/* 181 */         UI.this.updateMainPanel();
/*     */       }
/*     */     });
/* 184 */     this.search.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 186 */         UI.this.currentCommand = new SearchCommand(UI.this.hotelManager);
/* 187 */         UI.this.updateMainPanel();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     UI
 * JD-Core Version:    0.6.2
 */