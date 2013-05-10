/*    */ import java.util.ArrayList;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class SearchCommand
/*    */   implements Command
/*    */ {
/*    */   private HotelManager hotelManager;
/*  6 */   private String searchField = "";
/*  7 */   private String type = "ID";
/*  8 */   private Object[] searchResults = null;
/*    */ 
/*    */   public SearchCommand(HotelManager hotelManager) {
/* 11 */     this.hotelManager = hotelManager;
/*    */   }
/*    */ 
/*    */   public JPanel getPanel(View view) {
/* 15 */     return view.searchPanel();
/*    */   }
/*    */   public String getSearchField() {
/* 18 */     return this.searchField; } 
/* 19 */   public String getType() { return this.type; } 
/* 20 */   public Object[] getSearchResults() { return this.searchResults; } 
/*    */   public void setSearchField(String searchField) {
/* 22 */     this.searchField = searchField; } 
/* 23 */   public void setType(String type) { this.type = type; } 
/* 24 */   public void setSearchResults(Object[] searchResults) { this.searchResults = searchResults; }
/*    */ 
/*    */   public ArrayList search() {
/* 27 */     return this.hotelManager.findOccupant(this.type, this.searchField);
/*    */   }
/*    */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     SearchCommand
 * JD-Core Version:    0.6.2
 */