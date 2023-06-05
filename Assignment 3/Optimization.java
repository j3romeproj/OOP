import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the InventoryManagement class
        InventoryManagement inventoryManagement = new InventoryManagement();
        // Call the interfaced method to start the inventory management system
        inventoryManagement.interfaced();
    }
}

class InventoryManagement {
    // Create an instance of the Inventory class
    Inventory inventory = new Inventory();
    // Create a Scanner object to read user input
    Scanner scanner = new Scanner(System.in);
    // Variable to store user's input for yes/no questions
    String answer;

    // Method to display the main menu and handle user input
    public void interfaced() {
        int choice = 0;
        do {
            try {
                // Display the menu options
                System.out.println("\n\tInventory Management System");
                System.out.println("\n\t1. Add item to inventory");
                System.out.println("\t2. Remove item from inventory");
                System.out.println("\t3. Check quantity of an item");
                System.out.println("\t4. Exit");
                System.out.print("\n\tEnter your choice: ");
                // Read the user's choice
                choice = scanner.nextInt();

                scanner.nextLine();

                // Use a switch statement to perform actions based on user's choice
                switch (choice) {
                    case 1 -> addItemInterface(); // Call the addItemInterface method
                    case 2 -> removeItemInterface(); // Call the removeItemInterface method
                    case 3 -> checkItemInterface(); // Call the checkItemInterface method
                    case 4 -> System.out.println("\tExiting the program..."); // Exit the program
                    default -> System.out.println("\tInvalid choice. Please try again."); // Invalid input
                }
            } catch (InputMismatchException e) {
                System.out.print("\n\tYou've entered the wrong input. Please try again.\n");
                scanner.nextLine();
            }
        } while (choice != 4); // Continue looping until the user chooses to exit
    }

    // Method to handle adding items to the inventory
    public void addItemInterface() {
        int itemQuantity;
        String itemName;

        do {
            try {
                // Clear the console
                System.out.print("\033[H\033[2J");
                System.out.flush();

                // Prompt the user to enter the item name
                System.out.print("\n\tEnter Item Name to add: ");
                itemName = scanner.nextLine();

                // Prompt the user to enter the item quantity
                System.out.print("\tEnter Item Quantity: ");
                itemQuantity = scanner.nextInt();
                scanner.nextLine();

                // Call the addItem method of the Inventory class to add the item to the inventory
                inventory.addItem(itemName, itemQuantity);
            } catch (InputMismatchException e) {
                System.out.print("\n\tYou've entered wrong input. Please enter a number only.\n");
                scanner.nextLine();
            }
            // Ask the user if they want to add more items
            System.out.print("\n\tDo you want to add more items? [Yes/No]: ");
            answer = scanner.nextLine();
        } while (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("y")); // Continue looping if the answer is Yes or y
    }

    // Method to handle removing items from the inventory
    public void removeItemInterface() {
        do {
            // Clear the console
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Prompt the user to enter the item name to remove
            System.out.print("\n\tEnter Item Name to remove: ");
            String itemName = scanner.nextLine();

            // Call the removeItem method of the Inventory class to remove the item from the inventory
            inventory.removeItem(itemName);

            // Ask the user if they want to remove more items
            System.out.print("\n\tDo you want to remove more item?[Yes/No]: ");
            answer = scanner.nextLine();
        } while (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("y")); // Continue looping if the answer is Yes or y
    }

    // Method to handle checking the quantity of an item in the inventory
    public void checkItemInterface() {
        do {
            // Clear the console
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Prompt the user to enter the item name to check
            System.out.print("\n\tEnter Item Name to check: ");
            String itemName = scanner.nextLine();

            // Call the checkItem method of the Inventory class to check the quantity of the item in the inventory
            inventory.checkItem(itemName);

            // Ask the user if they want to check more items
            System.out.print("\n\tDo you want to check more item?[Yes/No]: ");
            answer = scanner.nextLine();
        } while (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("y")); // Continue looping if the answer is Yes or y
    }
}

class Inventory {
    // ArrayList to store items in the inventory
    ArrayList<ArrayList<String>> items = new ArrayList<>();
    // Variables for tracking the item existence, size, and index
    int checker, size, index;

    // Method to add an item to the inventory
    public void addItem(String itemName, int itemQuantity) {
        int tempItemQuantity = 0;
        checker = 0;
        size = items.size();

        // Iterate over the items in the inventory
        for (int i = 0; i < size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                // If the item already exists in the inventory, update its quantity
                tempItemQuantity = Integer.parseInt(items.get(i).get(1));
                tempItemQuantity = tempItemQuantity + itemQuantity;
                checker = 1;
                index = i;
                break;
            }
        }

        if (checker == 0) {
            // If the item doesn't exist, create a new ArrayList to store the item and its quantity
            ArrayList<String> item = new ArrayList<>();
            item.add(itemName);
            String strItemQuantity = Integer.toString(itemQuantity);
            item.add(strItemQuantity);
            items.add(item);
        } else {
            // If the item exists, update its quantity
            String strItemQuantity = Integer.toString(tempItemQuantity);
            items.get(index).set(1, strItemQuantity);
        }
        System.out.println("\tYou have been successfully added the " + itemQuantity + " pcs of '" + itemName + "' to the inventory");
    }

    // Method to remove an item from the inventory
    public void removeItem(String itemName) {
        checker = 0;
        size = items.size();

        // Iterate over the items in the inventory
        for (int i = 0; i < size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                // If the item is found, remove it from the inventory
                System.out.println("\tThe Item '" + items.get(i).get(0) + "' has been successfully removed");
                items.remove(i);
                checker = 1;
                break;
            }
        }

        if (checker == 0) {
            System.out.println("\tThere's no item named '" + itemName + "'");
        }
    }

    // Method to check the quantity of an item in the inventory
    public void checkItem(String itemName) {
        checker = 0;
        size = items.size();

        // Iterate over the items in the inventory
        for (int i = 0; i < size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                // If the item is found, display its quantity
                System.out.println("\tThe Quantity of '" + items.get(i).get(0) + "' in the inventory is " + items.get(i).get(1));
                checker = 1;
                break;
            }
        }

        if (checker == 0) {
            System.out.println("\tThere's no item named '" + itemName + "'");
        }
    }
}
