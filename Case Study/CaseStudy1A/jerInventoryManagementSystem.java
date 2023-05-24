import java.util.ArrayList;
import java.util.Scanner;
// Main Class
public class Main {
    public static void main (String[] args) {
        InventoryManagement inventoryManagement = new InventoryManagement();
        inventoryManagement.interfaced();
    }
}
// Inventory Management Class for the user interface(console)
class InventoryManagement {
    Inventory inventory = new Inventory();
    Scanner scanner = new Scanner(System.in);
    boolean isValid;
    String choice2;
    public void interfaced() {
        int choice = 0;
        do {
            // Exception Handling that handles if the user enter a wrong input
            do {
                try {
                    // Choices
                    System.out.println("\n\t1. Add item to inventory");
                    System.out.println("\t2. Remove item from inventory");
                    System.out.println("\t3. Check quantity of an item");
                    System.out.println("\t4. Exit");
                    System.out.print("\tEnter your choice: ");
                    choice2 = scanner.nextLine();
                    choice = Integer.parseInt(choice2);
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.print("\n\tYou've entered wrong input. Please try again.\n");
                    isValid = false;
                }
            } while (!isValid);
            // Switch Case Statement
            switch (choice) {
                case 1 -> addItemInterface();
                case 2 -> removeItemInterface();
                case 3 -> checkItemInterface();
                case 4 -> System.out.println("\tExiting the program...");
                default -> System.out.println("\tInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public void addItemInterface () {
        String itemQuantity;
        int itemQuantity2 = 0;

        do {
            // Clear Screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            System.out.print("\n\tEnter Item Name to add: ");
            String itemName = scanner.nextLine();
            // Exception Handling that handles if the user enter a wrong input
            do {
                try {
                    System.out.print("\tEnter Item Quantity: ");
                    itemQuantity = scanner.nextLine();
                    itemQuantity2 = Integer.parseInt(itemQuantity);
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.print("\n\tYou've entered wrong input. Please Number Only...\n");
                    isValid = false;
                }
            } while (!isValid);

            inventory.addItem(itemName , itemQuantity2);

            // Asking user if there's still want to remove from the list
            System.out.print("\n\tDo you want to add more item?[Yes/No]: ");
            choice2 = scanner.nextLine();
        } while (choice2.equalsIgnoreCase("Yes") || choice2.equalsIgnoreCase("y"));
    }

    public void removeItemInterface () {
        do {
            // Clear Screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            System.out.print("\n\tEnter Item Name to remove: ");
            String itemName = scanner.nextLine();

            inventory.removeItem(itemName);

            // Asking user if there's still want to remove from the list
            System.out.print("\n\tDo you want to remove more item?[Yes/No]: ");
            choice2 = scanner.nextLine();
        } while (choice2.equalsIgnoreCase("Yes") || choice2.equalsIgnoreCase("y"));
    }

    public void checkItemInterface () {
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            System.out.print("\n\tEnter Item Name to check: ");
            String itemName = scanner.nextLine();

            inventory.checkItem(itemName);
            // Asking user if there's still want to check from the list
            System.out.print("\n\tDo you want to check more item?[Yes/No]: ");
            choice2 = scanner.nextLine();
        } while (choice2.equalsIgnoreCase("Yes") || choice2.equalsIgnoreCase("y"));
    }
}
// Inventory Class to manage the items
class Inventory {
    ArrayList<ArrayList<String>> items = new ArrayList<>();
    int checker, size, sizes, tempIndex;
    // Function to add item in to the Inventory1
    public void addItem(String itemName, int itemQuantity2) {
        int tempItemQuantity = 0;
        checker = 0;
        // A Linear Search Algorithm that search and combine all the similar item inputted in the Inventory
        sizes = items.size();
        size = sizes - 1;
        for (int i = 0; i <= size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                int tempQuantity = Integer.parseInt(items.get(i).get(1));
                tempItemQuantity = tempQuantity + itemQuantity2;
                checker = 1;
                tempIndex = i;
                break;
            }
        }

        if (checker == 0) {
            ArrayList<String> item = new ArrayList<>();
            item.add(itemName);
            String itemQuantity = Integer.toString(itemQuantity2);
            item.add(itemQuantity);
            items.add(item);
        } else {
            String itemQuantity = Integer.toString(tempItemQuantity);
            items.get(tempIndex).set(1, itemQuantity);
        }
        // Output
        System.out.println("\tYou have been successfully added the " + itemQuantity2 + " pcs of '" + itemName + "' to the inventory");
    }
    // Function to remove item in to the Inventory
    public void removeItem(String itemName) {
        checker = 0;
        // A Linear Search Algorithm that search and remove an item in the ArrayList
        sizes = items.size();
        size = sizes - 1;
        for (int i = 0; i <= size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                System.out.println("\tThe Item '" + items.get(i).get(0) + "' has been successfully removed");
                items.remove(i);
                checker = 1;
                break;
            }
        }
        // Part of Linear Search Algorithm that check if the item is not on the list
        if (checker == 0) {
            System.out.println("\tThere's no item named '" + itemName + "'");
        }
    }

    // Function to check item in to the Inventory
    public void checkItem(String itemName) {
        checker = 0;
        // A Linear Search Algorithm that search and check the quantity of an item in the ArrayList
        sizes = items.size();
        size = sizes - 1;
        for (int i = 0; i <= size; i++) {
            if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                System.out.println("\tThe Quantity of '" + items.get(i).get(0) + "' in the inventory is " + items.get(i).get(1));
                checker = 1;
                break;
            }
        }
        // Part of Linear Search Algorithm that check if the item is not on the list
        if (checker == 0) {
            System.out.println("\tThere's no item named '" + itemName + "'");
        }
    }

}
