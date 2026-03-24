// Version: 8.0 (New Class)

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

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// ---------- BOOKING HISTORY ----------

class BookingHistory {

    // Stores confirmed bookings in order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// ---------- REPORT SERVICE ----------

class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("===== BOOKING HISTORY =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n===== BOOKING SUMMARY =====");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            String roomType = r.getRoomType();
            roomCount.put(roomType, roomCount.getOrDefault(roomType, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}

// ---------- MAIN CLASS ----------

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Initialize components
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("SINGLEROOM-1", "Alice", "Single Room"));
        history.addReservation(new Reservation("DOUBLEROOM-2", "Bob", "Double Room"));
        history.addReservation(new Reservation("SUITEROOM-3", "Charlie", "Suite Room"));
        history.addReservation(new Reservation("SINGLEROOM-4", "David", "Single Room"));

        // Admin views booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Admin generates summary report
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nNote: Reporting does not modify booking history.");
    }
}