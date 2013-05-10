/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.GregorianCalendar;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class CheckInCommand
/*    */   implements Command
/*    */ {
/*    */   private HotelManager hotelManager;
/*  8 */   private String ID = "";
/*  9 */   private String name = "";
/* 10 */   private String type = "Standard";
/* 11 */   private String company = "";
/* 12 */   private Date checkInDate = getCurrentDate();
/* 13 */   private boolean dataServiceRequired = false;
/* 14 */   private String ethernetAddress = "00:00:00:00:00:00";
/*    */   private Room selectedRoom;
/*    */ 
/*    */   public CheckInCommand(HotelManager hotelManager)
/*    */   {
/* 18 */     this.hotelManager = hotelManager;
/* 19 */     this.ID = hotelManager.generateID();
/*    */   }
/*    */ 
/*    */   public JPanel getPanel(View view) {
/* 23 */     return view.checkInPanel();
/*    */   }
/*    */   public String getID() {
/* 26 */     return this.ID; } 
/* 27 */   public String getName() { return this.name; } 
/* 28 */   public String getType() { return this.type; } 
/* 29 */   public String getCompany() { return this.company; } 
/* 30 */   public Date getCheckInDate() { return this.checkInDate; } 
/* 31 */   public boolean isDataServiceRequired() { return this.dataServiceRequired; } 
/* 32 */   public String getEthernetAddress() { return this.ethernetAddress; } 
/* 33 */   public Room getSelectedRoom() { return this.selectedRoom; } 
/*    */   public void setID(String ID) {
/* 35 */     this.ID = ID; } 
/* 36 */   public void setName(String name) { this.name = name; } 
/* 37 */   public void setType(String type) { this.type = type; } 
/* 38 */   public void setCompany(String company) { this.company = company; } 
/* 39 */   public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; } 
/* 40 */   public void setDataServiceRequired(boolean dataServiceRequired) { this.dataServiceRequired = dataServiceRequired; } 
/* 41 */   public void setEthernetAddress(String ethernetAddress) { this.ethernetAddress = ethernetAddress; } 
/*    */   public void setSelectedRoom(Room selectedRoom) {
/* 43 */     this.selectedRoom = selectedRoom;
/*    */   }
/* 45 */   public String checkIn() { String result = this.hotelManager.checkIn(new Occupant(this.ID, this.type, this.name, this.company), this.checkInDate, this.dataServiceRequired, this.ethernetAddress, this.selectedRoom);
/* 46 */     if (result.equals("Success")) {
/* 47 */       clear();
/*    */     }
/* 49 */     return result; }
/*    */ 
/*    */   private void clear()
/*    */   {
/* 53 */     this.name = (this.company = "");
/* 54 */     this.ID = this.hotelManager.generateID();
/* 55 */     this.checkInDate = getCurrentDate();
/* 56 */     this.dataServiceRequired = false;
/* 57 */     this.ethernetAddress = "00:00:00:00:00:00";
/* 58 */     this.type = "Standard";
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
 * Qualified Name:     CheckInCommand
 * JD-Core Version:    0.6.2
 */