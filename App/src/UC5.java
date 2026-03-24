// Version: 5.0 (New Class)

import java.util.*;

// ---------- RESERVATION (Booking Request) ----------

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

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// ---------- BOOKING REQUEST QUEUE ----------

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void viewRequests() {
        System.out.println("\n===== BOOKING REQUEST QUEUE =====");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (peek only, no removal)
    public Reservation peekNextRequest() {
        return queue.peek();
    }
}

// ---------- MAIN CLASS ----------

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Initialize Booking Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate Guest Requests (Arrival Order)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // View Queue (FIFO order preserved)
        bookingQueue.viewRequests();

        // Peek next request (without removing)
        System.out.println("\nNext Request to Process:");
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) {
            next.display();
        }

        System.out.println("\nNote: No rooms allocated yet. Inventory remains unchanged.");
    }
}