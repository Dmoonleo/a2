/*     */ import hkust.comp201.hw.HotelConfig;
/*     */ import hkust.comp201.hw.HotelConfigException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class HotelManager
/*     */ {
/*     */   private Room[][] rooms;
/*     */   private Hotel hotel;
/*   9 */   private static int roomRate = 3000;
/*     */   private UI ui;
/*  11 */   private Random generator = new Random(System.currentTimeMillis());
/*     */ 
/*     */   public HotelManager(String configFileName) {
/*  14 */     HotelConfig config = null;
/*     */     try {
/*  16 */       config = new HotelConfig(configFileName);
/*     */     }
/*     */     catch (HotelConfigException e) {
/*  19 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  23 */     int noOfFloors = config.getNumFloors();
/*  24 */     this.hotel = new Hotel(config.getName(), noOfFloors, config.getDataServiceChargePerDay());
/*     */ 
/*  27 */     this.rooms = new Room[noOfFloors][];
/*     */ 
/*  29 */     for (int i = 1; i <= noOfFloors; i++) {
/*  30 */       int noOfRoomsInFloor = config.getNumRoomsInFloor(i);
/*  31 */       this.rooms[(i - 1)] = new Room[noOfRoomsInFloor];
/*  32 */       for (int j = 1; j <= noOfRoomsInFloor; j++) {
/*  33 */         Room room = new Room(i, j, config.getRoomCapacity(i, j), config.getRoomType(i, j), config.getRoomRate(i, j));
/*  34 */         this.rooms[(i - 1)][(j - 1)] = room;
/*     */       }
/*     */     }
/*  37 */     new Thread(this.hotel).start();
/*  38 */     this.ui = new UI(this);
/*     */   }
/*     */ 
/*     */   public String getHotelName()
/*     */   {
/*  43 */     return this.hotel.getName();
/*     */   }
/*     */ 
/*     */   public ArrayList<Room> listAllOccupiedRooms() {
/*  47 */     ArrayList result = new ArrayList();
/*     */ 
/*  49 */     for (int i = 0; i < this.rooms.length; i++) {
/*  50 */       int upper = this.rooms[i].length;
/*  51 */       for (int j = 0; j < upper; j++) {
/*  52 */         if (!this.rooms[i][j].isAvailable()) {
/*  53 */           result.add(this.rooms[i][j]);
/*     */         }
/*     */       }
/*     */     }
/*  57 */     return result;
/*     */   }
/*     */ 
/*     */   public ArrayList<Room> listAllAvailableRooms() {
/*  61 */     ArrayList result = new ArrayList();
/*     */ 
/*  63 */     for (int i = 0; i < this.rooms.length; i++)
/*     */     {
/*  65 */       int upper = this.rooms[i].length;
/*  66 */       for (int j = 0; j < upper; j++) {
/*  67 */         if (this.rooms[i][j].isAvailable()) {
/*  68 */           result.add(this.rooms[i][j]);
/*     */         }
/*     */       }
/*     */     }
/*  72 */     return result;
/*     */   }
/*     */ 
/*     */   public String checkIn(Occupant person, Date checkInDate, boolean dataServiceRequired, String ethernetAddress, Room room)
/*     */   {
/*  91 */     if ((room.getType() == 1) && (dataServiceRequired)) {
/*  92 */       return "No data service for standard rooms";
/*     */     }
/*  94 */     if ((person.getType().equals("Standard")) && (dataServiceRequired)) {
/*  95 */       return "No data service for standard occupants";
/*     */     }
/*  97 */     if ((dataServiceRequired) && (!ethernetAddress.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}"))) {
/*  98 */       return "The format of the inputted ethernetAddress is invalid";
/*     */     }
/*     */ 
/* 101 */     if (!dataServiceRequired) {
/* 102 */       ethernetAddress = "";
/*     */     }
/* 104 */     room.setOccupation(new Occupation(checkInDate, dataServiceRequired, ethernetAddress, 
/* 105 */       person));
/*     */ 
/* 107 */     return "Success";
/*     */   }
/*     */ 
/*     */   public String checkOut(Date checkOutDate, Room room) {
/* 111 */     if ((checkOutDate == null) || (room == null)) {
/* 112 */       return "No input can be null";
/*     */     }
/*     */ 
/* 115 */     Occupation occupation = room.getOccupation();
/*     */ 
/* 117 */     if (occupation == null)
/* 118 */       return "The room has no occupant";
/* 119 */     if (occupation.getCheckInDate().compareTo(checkOutDate) >= 0) {
/* 120 */       return "Check-out date must be after check-in date";
/*     */     }
/*     */ 
/* 123 */     room.setOccupation(null);
/* 124 */     return "Success";
/*     */   }
/*     */ 
/*     */   public ArrayList findOccupant(String type, String value)
/*     */   {
/* 129 */     ArrayList result = new ArrayList();
/*     */ 
/* 131 */     for (int i = 0; i < this.rooms.length; i++) {
/* 132 */       int upper = this.rooms[i].length;
/* 133 */       for (int j = 0; j < upper; j++) {
/* 134 */         if (!this.rooms[i][j].isAvailable()) {
/* 135 */           Occupant o = this.rooms[i][j].getOccupation().getOccupant();
/* 136 */           if (((type.equals("ID")) && (o.getID().matches(value))) || 
/* 137 */             ((type.equals("Type")) && (o.getType().matches(value))) || 
/* 138 */             ((type.equals("Company")) && (o.getCompany().matches(value))) || (
/* 139 */             (type.equals("Name")) && (o.getName().matches(value)))) {
/* 140 */             result.add(this.rooms[i][j]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 145 */     return result;
/*     */   }
/*     */ 
/*     */   public Room getRoom(int floor, int num) {
/* 149 */     return this.rooms[(floor - 1)][(num - 1)];
/*     */   }
/*     */ 
/*     */   public String generateID() {
/* 153 */     char c = (char)(Math.abs(this.generator.nextInt() % 26) + 65);
/* 154 */     long i = Math.abs(this.generator.nextLong() % 100000000L);
/* 155 */     return c + new DecimalFormat("00000000").format(i);
/*     */   }
/*     */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     HotelManager
 * JD-Core Version:    0.6.2
 */