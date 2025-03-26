import java.util.*;
import java.text.NumberFormat;
import java.util.Locale;

public class BusReservationSystem {
    public static void main(String[] args) {
        ReservationSystem system = new ReservationSystem();
        system.runMenu();
    }
}

class Customer {
    private String name;
    private String contactNumber;
    private String email;
    private int age;

    public Customer(String name, String contactNumber, String email, int age) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.age = age;
    }

    // Getters
    public String getName() { return name; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return String.format("Customer: %s (Age: %d, Contact: %s)", name, age, contactNumber);
    }
}

class Bus {
    private String busNumber;
    private String route;
    private double fare;
    private int totalSeats;
    private List<Integer> availableSeats;

    public Bus(String busNumber, String route, double fare, int totalSeats) {
        this.busNumber = busNumber;
        this.route = route;
        this.fare = fare;
        this.totalSeats = totalSeats;
        
        // Initialize available seats
        this.availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.add(i);
        }
    }

    public boolean reserveSeat(int seatNumber) {
        return availableSeats.remove(Integer.valueOf(seatNumber));
    }

    public void cancelSeat(int seatNumber) {
        if (!availableSeats.contains(seatNumber)) {
            availableSeats.add(seatNumber);
            Collections.sort(availableSeats);
        }
    }

    // Getters
    public String getBusNumber() { return busNumber; }
    public String getRoute() { return route; }
    public double getFare() { return fare; }
    public List<Integer> getAvailableSeats() { return availableSeats; }
    public int getTotalSeats() { return totalSeats; }
}

class Reservation {
    private static int nextId = 1;
    private int id;
    private Customer customer;
    private Bus bus;
    private int seatNumber;

    public Reservation(Customer customer, Bus bus, int seatNumber) {
        this.id = nextId++;
        this.customer = customer;
        this.bus = bus;
        this.seatNumber = seatNumber;
    }

    // Getters
    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Bus getBus() { return bus; }
    public int getSeatNumber() { return seatNumber; }
}

class ReservationSystem {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Bus> buses = new HashMap<>();
    private List<Reservation> reservations = new ArrayList<>();
    private Queue<Customer> waitingList = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

    public void runMenu() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1: registerCustomer(); break;
                case 2: registerBus(); break;
                case 3: searchBuses(); break;
                case 4: reserveSeat(); break;
                case 5: cancelReservation(); break;
                case 6: displayReservations(); break;
                case 7: displayWaitingList(); break;
                case 8: 
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default: 
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Bus Reservation System ===");
        System.out.println("1. Register Customer");
        System.out.println("2. Register Bus");
        System.out.println("3. Search Buses");
        System.out.println("4. Reserve Seat");
        System.out.println("5. Cancel Reservation");
        System.out.println("6. Display Reservations");
        System.out.println("7. Display Waiting List");
        System.out.println("8. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    private void registerCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        int age;
        while (true) {
            try {
                System.out.print("Enter Age: ");
                age = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a number.");
            }
        }

        Customer customer = new Customer(name, contactNumber, email, age);
        customers.put(name, customer);
        System.out.println("Customer registered successfully!");
    }

    private void registerBus() {
        System.out.print("Enter Bus Number: ");
        String busNumber = scanner.nextLine();
        System.out.print("Enter Route (From-To): ");
        String route = scanner.nextLine();
        
        double fare;
        while (true) {
            try {
                System.out.print("Enter Fare: ");
                fare = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid fare. Please enter a number.");
            }
        }

        int totalSeats;
        while (true) {
            try {
                System.out.print("Enter Total Seats: ");
                totalSeats = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid seats. Please enter a number.");
            }
        }

        Bus bus = new Bus(busNumber, route, fare, totalSeats);
        buses.put(busNumber, bus);
        System.out.println("Bus registered successfully!");
    }

    private void searchBuses() {
        System.out.print("Enter Route to Search (From-To): ");
        String route = scanner.nextLine();

        boolean found = false;
        for (Bus bus : buses.values()) {
            if (bus.getRoute().equalsIgnoreCase(route)) {
                System.out.println("Bus Found:");
                System.out.println("Bus Number: " + bus.getBusNumber());
                System.out.println("Route: " + bus.getRoute());
                System.out.printf("Fare: %.2f\n", bus.getFare());
                System.out.println("Available Seats: " + bus.getAvailableSeats());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No buses found for the given route.");
        }
    }

    private void reserveSeat() {
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Bus Number: ");
        String busNumber = scanner.nextLine();

        Customer customer = customers.get(customerName);
        Bus bus = buses.get(busNumber);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }

        if (bus.getAvailableSeats().isEmpty()) {
            System.out.println("No seats available. Added to waiting list.");
            waitingList.add(customer);
            return;
        }

        System.out.println("Available Seats: " + bus.getAvailableSeats());
        System.out.print("Select Seat Number: ");
        
        int seatNumber;
        while (true) {
            try {
                seatNumber = Integer.parseInt(scanner.nextLine());
                if (bus.getAvailableSeats().contains(seatNumber)) {
                    break;
                } else {
                    System.out.println("Seat not available. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid seat number. Try again.");
            }
        }

        // Simulate payment
        System.out.printf("Total Fare: %.2f\n", bus.getFare());
        System.out.print("Confirm Payment (yes/no): ");
        String payment = scanner.nextLine();

        if (payment.equalsIgnoreCase("yes")) {
            bus.reserveSeat(seatNumber);
            Reservation reservation = new Reservation(customer, bus, seatNumber);
            reservations.add(reservation);
            System.out.println("Reservation Successful!");
            System.out.println("Reservation ID: " + reservation.getId());
        } else {
            System.out.println("Reservation Canceled.");
        }
    }

    private void cancelReservation() {
        System.out.print("Enter Reservation ID to Cancel: ");
        int reservationId;
        
        try {
            reservationId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Reservation ID.");
            return;
        }

        Reservation reservationToCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.getId() == reservationId) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            Bus bus = reservationToCancel.getBus();
            bus.cancelSeat(reservationToCancel.getSeatNumber());
            reservations.remove(reservationToCancel);
            System.out.println("Reservation Canceled Successfully.");
            
            // Process waiting list
            if (!waitingList.isEmpty()) {
                Customer nextCustomer = waitingList.poll();
                System.out.println("Next customer from waiting list can now book.");
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("=== Current Reservations ===");
        for (Reservation reservation : reservations) {
            System.out.println("Reservation ID: " + reservation.getId());
            System.out.println("Customer: " + reservation.getCustomer().getName());
            System.out.println("Bus: " + reservation.getBus().getBusNumber());
            System.out.println("Seat: " + reservation.getSeatNumber());
            System.out.println("------------------------");
        }
    }

    private void displayWaitingList() {
        if (waitingList.isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }

        System.out.println("=== Waiting List ===");
        for (Customer customer : waitingList) {
            System.out.println(customer);
        }
    }
}