class Library {
    private int ID;
    private String title;
    private String author;
    private boolean availability status;

    public Library(int ID, String tittle, String author, boolean availabilty status) {
    this.ID = ID;
    this.tittle = tittle;
    this.author = author;
    this.availabilty status = availabilty status;
   }
   
   public int getID() {
     return ID;
   }

   public String gettittle() {
     return tittle;
   }

   public String getauthor() {
     return author;
   }

   public void setavailability status(boolean availability status) {
     this.availability status = availability status;
   }
 }

   class Library {
    private ArrayList<Library> Library;
    private int nextID;

    public library() {
    Library = new ArrayList<Library>();
    nextID = 1;
   }
 
    public void displayBook() {
        if (books.isEmpty()) {
        System.out.printIn("No books available");
        } else {
           for (Book book : books) {
              String availability status = book.isAvailable() ? "(Available)" : "(Not Available)";
        System.out.printIn(book.getID() + ":" + book.gettittle() + " by " + book.getauthor() + " " + availability status);
        }
      }
    }

    public void borrowBook(int ID) {
       for (Book book : books) {
           if (book.getID() == ID) {
               if (book.isAvailable()) {
                   book.setavailability status(false);
          System.out.println("Book borrowed");
               } else {
          System.out.println("Error: Book not available");
               }
               return;
           }
       }
       System.out.println("Error: Book not found");
   }

   public void returnBook(int id) {
       for (Book book : books) {
           if (book.getID() == ID) {
               if (!book.isAvailable()) {
                   book.setavailabilty status(true);
                   System.out.println("Book returned");
               } else {
                   System.out.println("Error: Book not borrowed");
               }
               return;
           }
       }
       System.out.println("Error: Book not found");
   }
} 
