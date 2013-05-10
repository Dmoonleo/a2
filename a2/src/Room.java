/*    */ import java.text.SimpleDateFormat;
/*    */ 
/*    */ public class Room
/*    */ {
/*    */   private int floorNo;
/*    */   private int roomNo;
/*    */   private int capacity;
/*    */   private short type;
/*    */   private double rate;
/*    */   private Occupation occupation;
/*    */ 
/*    */   public Room(int floorNo, int roomNo, int capacity, short type, double rate)
/*    */   {
/* 15 */     this.floorNo = floorNo;
/* 16 */     this.roomNo = roomNo;
/* 17 */     this.capacity = capacity;
/* 18 */     this.type = type;
/* 19 */     this.rate = rate;
/*    */   }
/*    */ 
/*    */   public int getFloorNo() {
/* 23 */     return this.floorNo;
/*    */   }
/*    */ 
/*    */   public int getRoomNo() {
/* 27 */     return this.roomNo;
/*    */   }
/*    */ 
/*    */   public String getTypeString() {
/* 31 */     String typeString = null;
/* 32 */     if (this.type == 1)
/* 33 */       typeString = "Standard";
/* 34 */     else if (this.type == 2)
/* 35 */       typeString = "Executive";
/*    */     else {
/* 37 */       typeString = "Presidential";
/*    */     }
/* 39 */     return typeString;
/*    */   }
/*    */ 
/*    */   public int getCapacity() {
/* 43 */     return this.capacity;
/*    */   }
/*    */ 
/*    */   public short getType() {
/* 47 */     return this.type;
/*    */   }
/*    */ 
/*    */   public double getRate() {
/* 51 */     return this.rate;
/*    */   }
/*    */ 
/*    */   public void setOccupation(Occupation occupation) {
/* 55 */     this.occupation = occupation;
/*    */   }
/*    */ 
/*    */   public Occupation getOccupation() {
/* 59 */     return this.occupation;
/*    */   }
/*    */ 
/*    */   public boolean isAvailable() {
/* 63 */     return this.occupation == null;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 67 */     String dataServiceInfo = "";
/* 68 */     String occupantInfo = "None";
/*    */ 
/* 70 */     Occupant occupant = null;
/* 71 */     if (this.occupation != null) {
/* 72 */       occupant = this.occupation.getOccupant();
/*    */ 
/* 74 */       if ((this.type != 1) && (this.occupation.isDataServiceRequired())) {
/* 75 */         dataServiceInfo = dataServiceInfo + " Data service: In used (Ethernet address: " + 
/* 76 */           this.occupation.getEthernetAddress() + ")";
/*    */       }
/*    */ 
/* 79 */       occupantInfo = "<" + occupant + ">" + " Date of checking in: " + 
/* 80 */         new SimpleDateFormat("dd-MM-yyyy").format(this.occupation.getCheckInDate());
/*    */     }
/* 82 */     else if (this.type != 1) {
/* 83 */       dataServiceInfo = dataServiceInfo + "\n Data service: Not in used";
/*    */     }
/*    */ 
/* 86 */     return "Room No: " + this.floorNo + "-" + this.roomNo + ", Room Type: " + getTypeString() + 
/* 87 */       ", Capacity: " + this.capacity + ", Rate: " + this.rate + 
/* 88 */       dataServiceInfo + ", Occupant: " + occupantInfo;
/*    */   }
/*    */ 
/*    */   static enum Type
/*    */   {
/* 11 */     Standard, Executive, Presidential;
/*    */   }
/*    */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     Room
 * JD-Core Version:    0.6.2
 */