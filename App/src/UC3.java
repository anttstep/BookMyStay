// Version: 3.1 (Refactored)

import java.util.HashMap;
import java.util.Map;

// Inventory Class (Centralized State Management)
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor - Initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled modification)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display entire inventory
    public void displayInventory() {
        System.out.println("===== CURRENT ROOM INVENTORY =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display Initial Inventory
        inventory.displayInventory();

        System.out.println("\n--- Updating Inventory ---");

        // Update availability
        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Suite Room", 1);

        // Display Updated Inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}