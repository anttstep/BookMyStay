// Version: 6.0 (New Class)

import java.util.*;

// ---------- RESERVATION ----------

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------- BOOKING REQUEST QUEUE ----------

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO removal
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------- INVENTORY SERVICE ----------

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);
        if (count > 0) {
            inventory.put(roomType, count - 1);
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- BOOKING SERVICE (CORE LOGIC) ----------

class BookingService {

    private RoomInventory inventory;

    // Track allocated rooms (prevents duplicates)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type -> allocated room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1; // For generating unique IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processRequests(BookingRequestQueue queue) {

        System.out.println("===== PROCESSING BOOKINGS =====");

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing request for " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness using Set
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type to allocated rooms
                    roomAllocations
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                    // Decrement inventory immediately
                    inventory.decrementRoom(roomType);

                    System.out.println("Booking CONFIRMED for " + request.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Allocated Room ID: " + roomId);

                } else {
                    System.out.println("Error: Duplicate room ID detected!");
                }

            } else {
                System.out.println("Booking FAILED for " + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase() + "-" + (roomCounter++);
    }

    public void displayAllocations() {
        System.out.println("\n===== ROOM ALLOCATIONS =====");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- MAIN CLASS ----------

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // Should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process requests
        bookingService.processRequests(queue);

        // Show results
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed.");
    }
}