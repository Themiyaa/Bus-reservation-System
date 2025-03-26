import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Customer Class
class Customer {
    private String name;
    private String mobileNumber;
    private String email;
    private String city;
    private int age;

    // Constructor
    public Customer(String name, String mobileNumber, String email, String city, int age) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.city = city;
        this.age = age;
    }

    // Getters
    public String getName() { return name; }
    public String getMobileNumber() { return mobileNumber; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public int getAge() { return age; }

    // toString method for readable representation
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }
}

// Stack ADT Implementation
class CustomerStack {
    private List<Customer> stack;

    // Constructor
    public CustomerStack() {
        stack = new ArrayList<>();
    }

    // Push operation: Add a customer to the top of the stack
    public void push(Customer customer) {
        stack.add(customer); // Adds the customer to the end of the list (top of the stack)
    }

    // Pop operation: Remove and return the top customer from the stack
    public Customer pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot pop.");
        }
        return stack.remove(stack.size() - 1); // Removes the last element (top of the stack)
    }

    // Peek operation: View the top customer without removing it
    public Customer peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot peek.");
        }
        return stack.get(stack.size() - 1); // Returns the last element (top of the stack)
    }

    // isEmpty operation: Check if the stack is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Size operation: Return the total number of customers in the stack
    public int size() {
        return stack.size();
    }

    // DisplayAll operation: Display all customers from newest to oldest
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        System.out.println("Customers (Newest to Oldest):");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println(stack.get(i)); // Displays customers in reverse order (newest first)
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStack customerStack = new CustomerStack();

        while (true) {
            System.out.println("\nBus Reservation System Menu:");
            System.out.println("1. Register Customer");
            System.out.println("2. Display Customers (Newest to Oldest)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Input customer details
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Mobile Number: ");
                    String mobileNumber = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Create a new Customer object and push it onto the stack
                    Customer customer = new Customer(name, mobileNumber, email, city, age);
                    customerStack.push(customer);
                    System.out.println("Customer registered successfully!");
                    break;

                case 2:
                    // Display all customers from newest to oldest
                    customerStack.displayAll();
                    break;

                case 3:
                    // Exit the program
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}