// Version: 9.0 (New Class)

import java.util.*;

// ---------- CUSTOM EXCEPTION ----------

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// ---------- INVENTORY ----------

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {
        int count = getAvailability(roomType);

        if (count < 0) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (count == 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        inventory.put(roomType, count - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }
}

// ---------- VALIDATOR ----------

class InvalidBookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available.");
        }
    }
}

// ---------- BOOKING SERVICE ----------

class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {

        try {
            // Validate first (Fail-Fast)
            InvalidBookingValidator.validate(reservation, inventory);

            // Allocate room (safe after validation)
            inventory.decrementRoom(reservation.getRoomType());

            System.out.println("Booking SUCCESS for " + reservation.getGuestName()
                    + " (" + reservation.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

// ---------- MAIN CLASS ----------

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Test Cases

        // Valid booking
        bookingService.confirmBooking(new Reservation("Alice", "Single Room"));

        // Invalid room type
        bookingService.confirmBooking(new Reservation("Bob", "Luxury Room"));

        // No availability
        bookingService.confirmBooking(new Reservation("Charlie", "Suite Room"));

        // Empty guest name
        bookingService.confirmBooking(new Reservation("", "Double Room"));

        // Valid booking again
        bookingService.confirmBooking(new Reservation("David", "Double Room"));

        // Should fail (no more Double Rooms)
        bookingService.confirmBooking(new Reservation("Eve", "Double Room"));

        System.out.println("\nSystem continues running safely after errors.");
    }
}