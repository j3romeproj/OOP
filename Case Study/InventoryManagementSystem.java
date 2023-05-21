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
InventoryManagement {
    public void interfaced() {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);

        String choice2;
        int choice = 0;
        boolean isValid;
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
                case 1 -> inventory.addItem();
                case 2 -> inventory.removeItem();
                case 3 -> inventory.checkItem();
                case 4 -> System.out.println("\tExiting the program...");
                default -> System.out.println("\tInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}
// Inventory Class to manage the items
class Inventory {
    ArrayList<ArrayList<String>> items = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    String choice;
    int size, sizes, checker;
    // Function to add item in to the Inventory
    public void addItem() {
        String itemQuantity;
        int itemQuantity2 = 0, tempIndex = 0, tempItemQuantity = 0;
        boolean isValid;

        do {
            // Clear Screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            ArrayList<String> item = new ArrayList<>();
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
            // A Linear Search Algorithm that search and combine all the similar item inputted in the Inventory
            size = items.size();
            sizes = size - 1;
            for (int i = 0; i <= sizes; i++) {
                if (items.get(i).get(0).equalsIgnoreCase(itemName)) {
                    int tempQuantity = Integer.parseInt(items.get(i).get(1));
                    tempItemQuantity = tempQuantity + itemQuantity2;
                    checker = 1;
                    tempIndex = i;
                    break;
                }
            }
            // Conditional Statement that determine if the item is need to combine or not
            if (checker == 0) {
                item.add(itemName);
                itemQuantity = Integer.toString(itemQuantity2);
                item.add(itemQuantity);
                items.add(item);
            } else {
                itemQuantity = Integer.toString(tempItemQuantity);
                items.get(tempIndex).set(1, itemQuantity);
            }
            // Output
            System.out.println("\tYou have been successfully added the " + itemQuantity2 + " pcs of '" + itemName + "' to the inventory");
            // Asking user if there's still want to remove from the list
            System.out.print("\n\tDo you want to add more item?[Yes/No]: ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("y"));
    }
    // Function to remove item in to the Inventory
    public void removeItem() {
        do {
            checker = 0;
            // Clear Screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            System.out.print("\n\tEnter Item Name to remove: ");
            String itemName = scanner.nextLine();
            // A Linear Search Algorithm that search and remove an item in the ArrayList
            size = items.size();
            sizes = size - 1;
            for (int i = 0; i <= sizes; i++) {
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
            // Asking user if there's still want to remove from the list
            System.out.print("\n\tDo you want to remove more item?[Yes/No]: ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("y"));
    }
    // Function to check item in to the Inventory
    public void checkItem() {
        do {
            checker = 0;
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Input
            System.out.print("\n\tEnter Item Name to check: ");
            String itemName = scanner.nextLine();
            // A Linear Search Algorithm that search and check the quantity of an item in the ArrayList
            size = items.size();
            sizes = size - 1;
            for (int i = 0; i <= sizes; i++) {
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
            // Asking user if there's still want to check from the list
            System.out.print("\n\tDo you want to check more item?[Yes/No]: ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("y"));
    }
}
