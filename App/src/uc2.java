// Version: 2.0

public class BookMyStay {

    public static void main(String[] args) {

        // Create Room Objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static Availability Variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display Output
        System.out.println("===== Welcome to Book My Stay =====\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println("-----------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println("-----------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println("-----------------------------");

        System.out.println("\nThank you for using Book My Stay!");
    }
}