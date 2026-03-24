import java.util.*;

// ---------- DOMAIN MODEL ----------

abstract class Room {
    private String roomType;
    private double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000);
    }
}

// ---------- INVENTORY (READ-ONLY ACCESS USED HERE) ----------

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: unavailable
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// ---------- SEARCH SERVICE ----------

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("===== AVAILABLE ROOMS =====\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive Programming: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("-------------------------");
            }
        }
    }
}

// ---------- MAIN CLASS ----------

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize Room Objects (Domain Data)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Guest triggers search (Read-only operation)
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}