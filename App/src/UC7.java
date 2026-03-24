// Version: 7.0 (New Class)

import java.util.*;

// ---------- ADD-ON SERVICE ----------

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// ---------- ADD-ON SERVICE MANAGER ----------

class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service '" + service.getServiceName()
                + "' to Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.display();
        }
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }
}

// ---------- MAIN CLASS ----------

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Simulated reservation IDs (from Use Case 6)
        String reservation1 = "SINGLEROOM-1";
        String reservation2 = "DOUBLEROOM-2";

        // Initialize Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Create Add-On Services
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa Access", 800);

        // Guest selects services
        serviceManager.addService(reservation1, breakfast);
        serviceManager.addService(reservation1, wifi);
        serviceManager.addService(reservation2, spa);

        // View services
        serviceManager.viewServices(reservation1);
        serviceManager.viewServices(reservation2);

        // Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": ₹"
                + serviceManager.calculateTotalCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 + ": ₹"
                + serviceManager.calculateTotalCost(reservation2));

        System.out.println("\nNote: Booking and inventory remain unchanged.");
    }
}