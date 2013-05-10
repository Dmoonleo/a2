/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.GregorianCalendar;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ class CheckOutCommand
/*    */   implements Command
/*    */ {
/*    */   private HotelManager hotelManager;
/*  8 */   private String ID = "";
/*  9 */   private String name = "";
/* 10 */   private String type = "";
/* 11 */   private String company = "";
/* 12 */   private Date checkInDate = getCurrentDate();
/* 13 */   private Date checkOutDate = getCurrentDate();
/* 14 */   private boolean dataServiceRequired = false;
/* 15 */   private String ethernetAddress = "00:00:00:00:00:00";
/*    */   private Room selectedRoom;
/*    */ 
/*    */   public CheckOutCommand(HotelManager hotelManager)
/*    */   {
/* 19 */     this.hotelManager = hotelManager;
/*    */   }
/*    */ 
/*    */   public JPanel getPanel(View view) {
/* 23 */     return view.checkOutPanel();
/*    */   }
/*    */   public String getID() {
/* 26 */     return this.ID; } 
/* 27 */   public String getName() { return this.name; } 
/* 28 */   public String getType() { return this.type; } 
/* 29 */   public String getCompany() { return this.company; } 
/* 30 */   public Date getCheckInDate() { return this.checkInDate; } 
/* 31 */   public Date getCheckOutDate() { return this.checkOutDate; } 
/* 32 */   public boolean isDataServiceRequired() { return this.dataServiceRequired; } 
/* 33 */   public String getEthernetAddress() { return this.ethernetAddress; } 
/* 34 */   public Room getSelectedRoom() { return this.selectedRoom; } 
/*    */   public void setID(String ID) {
/* 36 */     this.ID = ID; } 
/* 37 */   public void setName(String name) { this.name = name; } 
/* 38 */   public void setType(String type) { this.type = type; } 
/* 39 */   public void setCompany(String company) { this.company = company; } 
/* 40 */   public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; } 
/* 41 */   public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; } 
/* 42 */   public void setDataServiceRequired(boolean dataServiceRequired) { this.dataServiceRequired = dataServiceRequired; } 
/* 43 */   public void setEthernetAddress(String ethernetAddress) { this.ethernetAddress = ethernetAddress; } 
/* 44 */   public void setSelectedRoom(Room selectedRoom) { this.selectedRoom = selectedRoom; }
/*    */ 
/*    */   public String checkOut() {
/* 47 */     String result = this.hotelManager.checkOut(this.checkOutDate, this.selectedRoom);
/* 48 */     if (result.equals("Success")) {
/* 49 */       clear();
/*    */     }
/* 51 */     return result;
/*    */   }
/*    */ 
/*    */   private void clear() {
/* 55 */     this.ID = (this.name = this.type = this.company = "");
/* 56 */     this.checkInDate = (this.checkOutDate = getCurrentDate());
/* 57 */     this.dataServiceRequired = false;
/* 58 */     this.ethernetAddress = "00:00:00:00:00:00";
/* 59 */     this.selectedRoom = null;
/*    */   }
/*    */ 
/*    */   private Date getCurrentDate()
/*    */   {
/*    */     try {
/* 65 */       return new SimpleDateFormat("dd-MM-yyyy").parse(new SimpleDateFormat("dd-MM-yyyy").format(new GregorianCalendar().getTime()));
/*    */     }
/*    */     catch (ParseException e) {
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     CheckOutCommand
 * JD-Core Version:    0.6.2
 */