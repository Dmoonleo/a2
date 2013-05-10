// All code here are decompiled from the demo jar file!


/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Hotel
/*     */   implements Runnable
/*     */ {
/*     */   private String name;
/*     */   private int numFloors;
/*     */   private double dataServiceCharge;
/*     */   private Calendar currentDate;
/*     */   static HotelManager mgr;
/*     */ 
/*     */   public Hotel(String n, int num, double charge)
/*     */   {
/*  20 */     this.name = n;
/*  21 */     this.numFloors = num;
/*  22 */     this.dataServiceCharge = charge;
/*  23 */     this.currentDate = Calendar.getInstance();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  28 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void consoleStart()
/*     */   {
/*  35 */     BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
/*     */ 
/*  38 */     System.out.println("***********Hotel Room Management System************");
/*  39 */     System.out.println("  Welcome to \"" + this.name + "\" hotel.");
/*  40 */     System.out.println("  Today is: " + this.currentDate.get(5) + "-" + (
/*  41 */       this.currentDate.get(2) + 1) + "-" + this.currentDate.get(1));
/*  42 */     System.out.println("***************************************************");
/*  43 */     int selection = 0;
/*  44 */     Room room = null;
/*  45 */     Collection roomVector = null;
/*  46 */     String ethernet = null;
/*  47 */     boolean data = false;
/*  48 */     while (selection != 9)
/*     */     {
/*  50 */       System.out.print("Select desired operation:\nList all occupied rooms [1]\nList all available rooms [2]\nCheck availability for particular room type [3]\nCheck in [4]\nCheck out [5]\nFind an occupant [6]\nPrint room content [7]\nIncrement date [8]\nQuit [9]\nChoice: ");
/*     */       try
/*     */       {
/*  54 */         switch (selection = Integer.parseInt(inReader.readLine()))
/*     */         {
/*     */         case 1:
/*  57 */           consolePrintRooms(mgr.listAllOccupiedRooms());
/*  58 */           break;
/*     */         case 2:
/*  60 */           consolePrintRooms(mgr.listAllAvailableRooms());
/*  61 */           break;
/*     */         case 3:
/*  63 */           System.out.print("Which type of room to check? Standard [1], Executive [2], Presidential [3]: ");
/*  64 */           switch (Integer.parseInt(inReader.readLine()))
/*     */           {
/*     */           case 1:
/*  67 */             roomVector = checkAvailability((short)1);
/*  68 */             break;
/*     */           case 2:
/*  70 */             roomVector = checkAvailability((short)2);
/*  71 */             break;
/*     */           case 3:
/*  73 */             roomVector = checkAvailability((short)3);
/*  74 */             break;
/*     */           default:
/*  76 */             System.out.println("Invlid selection!");
/*  77 */             roomVector = null;
/*     */           }
/*  79 */           if (roomVector != null)
/*  80 */             consolePrintRooms(roomVector);
/*  81 */           break;
/*     */         case 4:
/*  84 */           System.out.println("Which room to check in?");
/*  85 */           room = consoleGetRoom(inReader);
/*  86 */           if (room != null)
/*     */           {
/*  88 */             if (!room.isAvailable())
/*     */             {
/*  90 */               System.out.println("Room is occupied.");
/*     */             }
/*     */             else
/*     */             {
/*  94 */               System.out.println("Check in the room: ");
/*  95 */               System.out.println(room.toString());
/*  96 */               System.out.print("Yes [Y], No [N]: ");
/*  97 */               if (inReader.readLine().toUpperCase().equals("Y"))
/*     */               {
/* 100 */                 Occupant occupant = consoleGetOccupantInfo(inReader, room);
/* 101 */                 if (occupant != null)
/*     */                 {
/* 103 */                   if (occupant.getType().equals("Business")) {
/* 104 */                     if (room.getType() != 2)
/*     */                     {
/* 106 */                       System.out.println("Warning: Data service is not available in room " + room.getRoomNo());
/*     */                     }
/*     */                     else
/*     */                     {
/* 110 */                       System.out.print("Is data service required? Yes [Y], No [N]: ");
/* 111 */                       if (inReader.readLine().toUpperCase().equals("Y"))
/*     */                       {
/* 113 */                         data = true;
/* 114 */                         ethernet = null;
/* 115 */                         while (!ethernet.matches("([0-9a-eA-E]{2}:){5}[0-9a-eA-E]{2}"))
/*     */                         {
/* 117 */                           System.out.print("What is the ethernet address: ");
/* 118 */                           ethernet = inReader.readLine();
/* 119 */                           if (!ethernet.matches("([0-9a-eA-E]{2}:){5}[0-9a-eA-E]{2}"))
/*     */                           {
/* 121 */                             System.out.println("Format not correct!");
/*     */                           }
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/* 127 */                   mgr.checkIn(occupant, this.currentDate.getTime(), data, ethernet, room);
/* 128 */                   System.out.println("Done."); }  }  } 
/* 129 */           }break;
/*     */         case 5:
/* 136 */           System.out.println("Which room to check out?");
/* 137 */           room = consoleGetRoom(inReader);
/* 138 */           if (room != null)
/*     */           {
/* 140 */             if (room.isAvailable())
/*     */             {
/* 142 */               System.out.println("Room is not occupied.");
/*     */             }
/*     */             else {
/* 145 */               System.out.print("Check out occupant: ");
/* 146 */               System.out.println(room.getOccupation().getOccupant().toString());
/* 147 */               System.out.print("\nYes [Y], No [N]: ");
/* 148 */               if (inReader.readLine().toUpperCase().equals("Y"))
/*     */               {
/* 150 */                 if ((room.getType() == 2) && (room.getOccupation().isDataServiceRequired()))
/*     */                 {
/* 152 */                   System.out.println("Room rate: " + (room.getRate() + 20.0D) + " (Data Service used)");
/*     */                 }
/*     */                 else
/*     */                 {
/* 156 */                   System.out.println("Room rate: " + room.getRate());
/*     */                 }
/* 158 */                 Occupation o = room.getOccupation();
/* 159 */                 System.out.println("Date of checking in: " + o.getCheckInDate().toString());
/* 160 */                 System.out.println("Date of checking out: " + this.currentDate.toString());
/* 161 */                 System.out.println("Number of days stayed: " + noDaysStayed(this.currentDate, o.getCheckInDate()));
/* 162 */                 System.out.println("Bill: " + o.getBill(room, this.currentDate));
/* 163 */                 mgr.checkOut(this.currentDate.getTime(), room);
/* 164 */                 System.out.println("Done.");
/*     */               }
/*     */               else
/*     */               {
/* 168 */                 System.out.println("Check out canceled.");
/*     */               }
/*     */             }
/*     */           }
/* 170 */           break;
/*     */         case 6:
/* 172 */           System.out.print("What is the searching field? Name [1], Member ID [2]: ");
/* 173 */           switch (Integer.parseInt(inReader.readLine()))
/*     */           {
/*     */           case 1:
/* 176 */             System.out.print("Occupant's name: ");
/* 177 */             roomVector = mgr.findOccupant("Name", inReader.readLine());
/* 178 */             consolePrintRooms(roomVector);
/* 179 */             break;
/*     */           case 2:
/* 181 */             System.out.print("Occupant's member ID: ");
/* 182 */             String memberID = inReader.readLine();
/* 183 */             if (!memberID.matches("[a-zA-Z]\\d{8}"))
/*     */             {
/* 185 */               System.out.println("Format not correct.");
/*     */             }
/*     */             else {
/* 188 */               roomVector = mgr.findOccupant("ID", memberID);
/* 189 */               consolePrintRooms(roomVector);
/*     */             }break;
/*     */           }
/* 192 */           break;
/*     */         case 7:
/* 195 */           System.out.println("Which room to print?");
/* 196 */           room = consoleGetRoom(inReader);
/* 197 */           if (room != null)
/*     */           {
/* 199 */             System.out.println(room.toString());
/* 200 */           }break;
/*     */         case 8:
/* 203 */           System.out.println("Current date is: " + this.currentDate.get(5) + "-" + (
/* 204 */             this.currentDate.get(2) + 1) + "-" + this.currentDate.get(1));
/* 205 */           System.out.print("Number of day(s) to increase: ");
/* 206 */           this.currentDate.add(5, Integer.parseInt(inReader.readLine()));
/* 207 */           System.out.println("Today is: " + this.currentDate.get(5) + "-" + (
/* 208 */             this.currentDate.get(2) + 1) + "-" + this.currentDate.get(1));
/* 209 */           break;
/*     */         case 9:
/* 211 */           System.out.println("Thanks for using " + getClass().getName() + " Room Management System.");
/* 212 */           break;
/*     */         default:
/* 215 */           System.out.println("Invalid selection!");
/*     */         }
/*     */ 
/* 218 */         System.out.println("Press \"Enter\" key to continue...");
/* 219 */         inReader.readLine();
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 223 */         System.err.println("Error: " + ioe);
/* 224 */         ioe.printStackTrace();
/* 225 */         System.exit(1);
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 229 */         System.out.println("Invalid selection!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void consolePrintRooms(Collection<Room> rooms)
/*     */   {
/* 237 */     System.out.println("Number of matches: " + rooms.size());
/* 238 */     for (Room r : rooms)
/*     */     {
/* 240 */       System.out.println(r.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector<Room> checkAvailability(short roomType)
/*     */   {
/* 247 */     Vector rooms = new Vector();
/* 248 */     ArrayList<Room> goodrooms = mgr.listAllAvailableRooms();
/* 249 */     for (Room r : goodrooms) {
/* 250 */       if (r.getType() == roomType) {
/* 251 */         rooms.add(r);
/*     */       }
/*     */     }
/* 254 */     return rooms;
/*     */   }
/*     */ 
/*     */   public Room consoleGetRoom(BufferedReader inReader)
/*     */     throws IOException, NumberFormatException
/*     */   {
/*     */     try
/*     */     {
/* 262 */       System.out.print("Floor: ");
/* 263 */       int floor = Integer.parseInt(inReader.readLine());
/* 264 */       System.out.print("Room Number: ");
/* 265 */       int roomNo = Integer.parseInt(inReader.readLine());
/* 266 */       return mgr.getRoom(floor, roomNo);
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException ae)
/*     */     {
/* 270 */       System.out.println("Room selected doesn't exist.");
/* 271 */     }return null;
/*     */   }
/*     */ 
/*     */   public Occupant consoleGetOccupantInfo(BufferedReader inReader, Room room)
/*     */     throws IOException, NumberFormatException
/*     */   {
/* 277 */     System.out.println("Input occupant's information:");
/* 278 */     System.out.print("Name: ");
/* 279 */     String name = inReader.readLine();
/* 280 */     String memberID = "";
/* 281 */     while (!memberID.matches("[a-zA-Z]\\d{8}"))
/*     */     {
/* 283 */       System.out.print("Member ID: ");
/* 284 */       memberID = inReader.readLine();
/* 285 */       if (!memberID.matches("[a-zA-Z]\\d{8}"))
/*     */       {
/* 287 */         System.out.println("Format not correct.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 292 */     System.out.print("Type (Standard [1], Business [2]): ");
/* 293 */     switch (Integer.parseInt(inReader.readLine()))
/*     */     {
/*     */     case 1:
/* 296 */       return new Occupant(memberID, "Standard", name, "");
/*     */     case 2:
/* 298 */       String company = null;
/* 299 */       String ethernetAddr = "";
/* 300 */       System.out.print("What is the name of the company occupant working for: ");
/* 301 */       company = inReader.readLine();
/*     */ 
/* 303 */       return new Occupant(memberID, "Business", name, company);
/*     */     }
/* 305 */     return null;
/*     */   }
/*     */ 
/*     */   private int noDaysStayed(Calendar checkoutDate, Date checkinDate)
/*     */   {
			  int i = 0;
/* 311 */     Calendar tempCheckinDate = Calendar.getInstance();
/* 312 */     tempCheckinDate.setTime(checkinDate);
/* 313 */     for (i = 0; 
/* 314 */       checkoutDate.after(tempCheckinDate); i++)
/*     */     {
/* 316 */       tempCheckinDate.add(5, 1);
/*     */     }
/*     */ 
/* 319 */     return i;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 323 */     if (args.length == 1)
/* 324 */       mgr = new HotelManager(args[0]);
/*     */     else
/* 326 */       System.out.println("Usage: java Hotel filename");
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 332 */     consoleStart();
/*     */   }
/*     */ }

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     Hotel
 * JD-Core Version:    0.6.2
 */