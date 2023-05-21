import java.util.ArrayList;
import java.util.Scanner;
// Main Class
public class Main {
    public static void main (String[] args) {
        LibraryManagement libraryManagement = new LibraryManagement();
        libraryManagement.interfaced();
    }
}

class LibraryManagement {
    public void interfaced() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        Book book = new Book();

        String choice2;
        int choice = 0;
        boolean isValid;
        do {
            do {
                try{
                    System.out.println("\n--- Library Management System ---");
                    System.out.println("1. Add a book");
                    System.out.println("2. Remove a book");
                    System.out.println("3. Display available books");
                    System.out.println("4. Borrow a book");
                    System.out.println("5. Return a book");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice (1-6): ");
                    choice2 = scanner.nextLine();
                    choice = Integer.parseInt(choice2);
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.print("\n\tYou've entered wrong input. Please try again.\n");
                    isValid = false;
                }
            } while (!isValid);

            switch (choice) {
                case 1 -> book.addBook();
                case 2 -> book.removeBook();
                case 3 -> library.displayBook();
                case 4 -> library.borrowBook();
                case 5 -> library.returnBook();
                case 6 -> System.out.println("\tExiting the program...");
                default -> System.out.println("\tInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}
class Book {
    public void addBook() {

    }

    public void removeBook() {

    }
}
class Library {

    public void displayBook() {

    }

    public void borrowBook() {

    }

    public void returnBook() {

    }
}
