/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Occupation
/*    */ {
/*    */   private Date checkInDate;
/*    */   private boolean dataServiceRequired;
/*    */   private String ethernetAddress;
/*    */   private Occupant occupant;
/*    */ 
/*    */   public Occupation(Date checkInDate, boolean dataServiceRequired, String ethernetAddress, Occupant occupant)
/*    */   {
/* 11 */     this.checkInDate = checkInDate;
/* 12 */     this.dataServiceRequired = dataServiceRequired;
/* 13 */     this.ethernetAddress = ethernetAddress;
/* 14 */     this.occupant = occupant;
/*    */   }
/*    */ 
/*    */   public Occupant getOccupant() {
/* 18 */     return this.occupant;
/*    */   }
/*    */ 
/*    */   public Date getCheckInDate() {
/* 22 */     return this.checkInDate;
/*    */   }
/*    */ 
/*    */   public boolean isDataServiceRequired() {
/* 26 */     return this.dataServiceRequired;
/*    */   }
/*    */ 
/*    */   public String getEthernetAddress() {
/* 30 */     return this.ethernetAddress;
/*    */   }
/*    */ 
/*    */   public int duration(Calendar checkoutDate)
/*    */   {
			 int i = 0;
/* 35 */     Calendar tempCheckinDate = Calendar.getInstance();
/* 36 */     tempCheckinDate.setTime(this.checkInDate);
/* 37 */     for (i = 0; 
/* 38 */       checkoutDate.after(tempCheckinDate); i++)
/*    */     {
/* 40 */       tempCheckinDate.add(5, 1);
/*    */     }
/*    */ 
/* 43 */     return i;
/*    */   }
/*    */ 
/*    */   public double getBill(Room r, Calendar checkoutdate) {
/* 47 */     int days = duration(checkoutdate);
/* 48 */     double bill = days * (r.getRate() + (this.dataServiceRequired ? 20 : 0));
/* 49 */     return bill;
/*    */   }
/*    */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     Occupation
 * JD-Core Version:    0.6.2
 */