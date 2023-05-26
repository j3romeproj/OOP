import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Main Class
public class Main {
    public static void main (String[] args) {
        LibraryManagement libraryManagement = new LibraryManagement();
        libraryManagement.interfaced();
    }
}

class LibraryManagement {
    Scanner scanner = new Scanner(System.in);
    Library library;
    boolean isValid;
    int bookID = 0;
    public LibraryManagement() {
        this.library = new Library();
    }
    public void interfaced() {

        String choice2;
        int choice = 0;
        do {
            do {
                try{
                    System.out.println("\nLibrary Management System");
                    System.out.println("1. Add a book");
                    System.out.println("2. Remove a book");
                    System.out.println("3. Display available books");
                    System.out.println("4. Borrow a book");
                    System.out.println("5. Return a book");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    choice2 = scanner.nextLine();
                    choice = Integer.parseInt(choice2);
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.print("\n\tYou've entered wrong input. Please try again.\n");
                    isValid = false;
                }
            } while (!isValid);

            switch (choice) {
                case 1 -> addBookInterface();
                case 2 -> removeBookInterface();
                case 3 -> displayBookInterface();
                case 4 -> borrowBookInterface();
                case 5 -> returnBookInterface();
                case 6 -> System.out.println("\tExiting the program...");
                default -> System.out.println("\tInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public void addBookInterface() {
        System.out.print("Enter a Book Title: ");
        String bookTitle = scanner.nextLine();
        System.out.print("Enter a Book Author: ");
        String bookAuthor = scanner.nextLine();

        Book book = new Book(bookTitle, bookAuthor);
        library.addBook(book);
    }

    public void removeBookInterface() {
        do {
            try {
                System.out.print("Enter a Book ID: ");
                bookID = scanner.nextInt();
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("\n\tYou've entered wrong input. Please try again.\n");
                isValid = false;
            }
        } while (!isValid);

        library.removeBook(bookID);
    }

    public void displayBookInterface() {
        library.displayBook();
    }

    public void borrowBookInterface() {
        do {
            try {
                System.out.print("Enter the BookID of the book that you wanted to borrow: ");
                bookID = scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("\n\tYou've entered wrong input. Please try again.\n");
                isValid = false;
            }
        } while (!isValid);

        library.borrowBook(bookID);
    }

    public void returnBookInterface() {
        do {
            try {
                System.out.print("Enter the BookID of the book that you wanted to return: ");
                bookID = scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.print("\n\tYou've entered wrong input. Please try again.\n");
                isValid = false;
            }
        } while (!isValid);
        
        library.returnBook(bookID);
    }
}
class Library {
    private final List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int bookID) {
        for (Book book : books) {
            if (book.getId() == bookID) {
                books.remove(book);
                return;
            }
        }
    }

    public void displayBook() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println("\nID: " + book.getId() + "\nTitle: " + book.getTitle() + "\nAuthor: " + book.getAuthor() + "\n");
            }
        }

        System.out.println("Not Available Books:");
        for (Book book : books) {
            if (!book.isAvailable()) {
                System.out.println("\nID: " + book.getId() + "\nTitle: " + book.getTitle() + "\nAuthor: " + book.getAuthor() + "\n");
            }
        }
    }

    public void borrowBook(int bookID) {
        for (Book book : books) {
            if (book.getId() == bookID) {
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    System.out.println("You've been successfully borrowed the book.");
                } else {
                    System.out.println("Sorry, the book is already borrowed. Thank you!");
                }
                return;
            }
        }
        System.out.println("Sorry, but book that you entered cannot found.");
    }

    public void returnBook (int bookID) {
        for (Book book : books) {
            if (book.getId() == bookID) {
                if (!book.isAvailable()) {
                    book.setAvailable(true);
                    System.out.println("You've been successfully return the book.");
                } else {
                    System.out.println("The book is already available. Thank you!");
                }
                return;
            }
        }
        System.out.println("Sorry, but book that you entered cannot found.");
    }

}

class Book {
    private static int nextID = 1;
    private final int bookID;
    private final String bookTitle;
    private final String bookAuthor;
    private boolean available;

    public Book(String title, String author) {
        this.bookID = nextID++;
        this.bookTitle = title;
        this.bookAuthor = author;
        this.available = true;
    }

    public int getId() {
        return bookID;
    }

    public String getTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return bookAuthor;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
