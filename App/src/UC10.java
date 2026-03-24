// Version: 10.0 (New Class)

import java.util.*;

// ---------- RESERVATION ----------

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------- INVENTORY ----------

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- BOOKING HISTORY ----------

class BookingHistory {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        confirmedBookings.remove(reservationId);
    }

    public boolean exists(String reservationId) {
        return confirmedBookings.containsKey(reservationId);
    }
}

// ---------- CANCELLATION SERVICE ----------

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        // Validate existence
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation FAILED: Reservation not found.");
            return;
        }

        // Retrieve reservation
        Reservation reservation = history.getReservation(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.incrementRoom(reservation.getRoomType());

        // Remove from booking history
        history.removeReservation(reservationId);

        System.out.println("Cancellation SUCCESS for " + reservation.getGuestName());
        System.out.println("Room released: " + reservation.getRoomType());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recent Cancellations): " + rollbackStack);
    }
}

// ---------- MAIN CLASS ----------

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Simulated confirmed bookings
        history.addReservation(new Reservation("S1", "Alice", "Single Room"));
        history.addReservation(new Reservation("D1", "Bob", "Double Room"));

        // Cancel valid booking
        cancellationService.cancelBooking("S1");

        // Attempt invalid cancellation
        cancellationService.cancelBooking("S1"); // already cancelled

        // Cancel another booking
        cancellationService.cancelBooking("D1");

        // Display rollback history
        cancellationService.displayRollbackStack();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nSystem state remains consistent after rollback.");
    }
}