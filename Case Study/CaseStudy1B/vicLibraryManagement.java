class LibraryManagement {
    Scanner scanner = new Scanner(System.in);
    Library library = new Library();
    public void interfaced() {

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
                case 1 -> addBookInterface();
                case 2 -> removeBookInterface();
                case 3 -> library.displayBook();
                case 4 -> library.borrowBook();
                case 5 -> library.returnBook();
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

        library.addBook(bookTitle, bookAuthor);
    }

    public void removeBookInterface() {
        System.out.print("Enter a Book ID: ");
        int bookID = scanner.nextInt();

        library.removeBook(bookID);
    }
}
