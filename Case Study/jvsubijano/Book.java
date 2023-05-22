
class Book {
    private String title;
    private String author;
    ArrayList<Book> books = new ArrayList<>();
    Scanner  scanner = new Scanner (System.in);

    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Title of the Book: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author of the Book: ");
        String author = scanner.nextLine();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        library.book.add(book);

        System.out.println("/t Book Added Successfully!");

    }
    public void removeBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the book to remove: ");
        int bookId = scanner.nextInt();

        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == bookId) {
                books.remove(i);
                found = true;
                System.out.println("\tBook removed successfully!");
                break;

        if (!removed) {
            System.out.println("\tBook not found in the library.");
        }
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;

    }
    public String setAuthor(); {
        return author;
    }
}
