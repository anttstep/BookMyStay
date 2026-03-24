// Version: 12.0 (New Class)

import java.io.*;
import java.util.*;

// ---------- RESERVATION ----------

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// ---------- SYSTEM STATE (SNAPSHOT) ----------

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;
    private List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }
}

// ---------- PERSISTENCE SERVICE ----------

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        return null; // fallback
    }
}

// ---------- MAIN CLASS ----------

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Attempt recovery
        SystemState recoveredState = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> history;

        if (recoveredState != null) {
            inventory = recoveredState.getInventory();
            history = recoveredState.getBookingHistory();

            System.out.println("\nRecovered Inventory: " + inventory);
            System.out.println("Recovered Booking History:");
            for (Reservation r : history) {
                r.display();
            }

        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            history = new ArrayList<>();
            history.add(new Reservation("R1", "Alice", "Single Room"));
            history.add(new Reservation("R2", "Bob", "Double Room"));

            System.out.println("\nInitialized fresh system state.");
        }

        // Simulate system shutdown (save state)
        SystemState state = new SystemState(inventory, history);
        persistence.save(state);

        System.out.println("\nSystem ready for restart with persisted data.");
    }
}