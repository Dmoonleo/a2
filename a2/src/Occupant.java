/*    */ public class Occupant
/*    */ {
/*    */   private String ID;
/*    */   private String type;
/*    */   private String name;
/*    */   private String company;
/*    */ 
/*    */   public Occupant(String ID, String type, String name, String company)
/*    */   {
/*  8 */     this.ID = ID;
/*  9 */     this.type = type;
/* 10 */     this.name = name;
/* 11 */     this.company = company;
/*    */   }
/*    */ 
/*    */   public String getID() {
/* 15 */     return this.ID;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 19 */     return this.type;
/*    */   }
/*    */ 
/*    */   public String getCompany() {
/* 23 */     return this.company;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 31 */     return "Name: " + this.name + ", Member ID: " + this.ID + ", Company: " + this.company;
/*    */   }
/*    */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     Occupant
 * JD-Core Version:    0.6.2
 */