import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        LibraryStorage storage = new LibraryStorage();
        Library library = storage.load();


        int menuChoice;
        do {
            System.out.println("Book Nook - Personal Library System");
            System.out.println("1: Show all Books");
            System.out.println("2: Search Book by Title");
            System.out.println("3: Search Book by Author");
            System.out.println("4: Search Book by Reading Status");
            System.out.println("5: Update Reading Progress");
            System.out.println("6: Add new Book");
            System.out.println("7: Exit");
            System.out.println("Please enter your choice");

            menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice){
                case 1 -> library.getAllBooks();
                case 2 ->{
                    System.out.println("Enter Book Title");
                    String title = scanner.nextLine();
                    Book found = library.getBookByTitle(title);
                    if(found != null){
                        System.out.println("Book found:\n" + library.getBookByTitle(title));
                    }else{
                        System.out.println("Book not found");
                    }
                }
                case 3 ->{
                    System.out.println("Enter Author");
                    String author = scanner.nextLine();
                    Book found = library.getBookByAuthor(author);
                    if(found != null){
                        System.out.println("Book found:\n" + library.getBookByAuthor(author) );
                    }else{
                        System.out.println("Book not found");
                    }
                }
                case 4 -> {
                    System.out.println("1: show unread books");
                    System.out.println("2: show read books");
                    System.out.println("3: show currently reading books");
                    int readingChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (readingChoice){
                        case 1 -> {

                            library.getBookByStatus(Book.ReadingStatus.unread);
                        }
                        case 2 -> {
                            library.getBookByStatus(Book.ReadingStatus.read);
                        }
                        case 3 -> {
                            library.getBookByStatus(Book.ReadingStatus.ongoing);
                        }
                        default -> System.out.println("no books found.");
                    }
                }
                case 5 -> {
                    System.out.println("Enter Book Title you want to update:");
                    String title = scanner.nextLine();
                    Book found = library.getBookByTitle(title);
                    if(found != null){
                        System.out.println("Book found:" + found);
                        System.out.println("Enter your current reading status (unread, read or ongoing");
                        Book.ReadingStatus status = Book.ReadingStatus.valueOf(scanner.nextLine());

                        System.out.println("Enter your current page number");
                        int currentPage = scanner.nextInt();
                        scanner.nextLine();

                        found.updateReadingStatus(status,currentPage);
                        storage.save(library);
                    }else{
                        System.out.println("Book not found");
                    }

                }
                case 6 -> {
                    System.out.println("Enter Book title");
                    String title = scanner.nextLine();
                    System.out.println("Enter Author name");
                    String author = scanner.nextLine();
                    System.out.println("Enter pageCount");
                    int pageCount = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter current reading Status");
                    Book.ReadingStatus status = Book.ReadingStatus.valueOf(scanner.nextLine());
                    int currentPage;
                    if(status.equals(Book.ReadingStatus.unread)){
                        currentPage = 0;
                    }else {System.out.println("Enter your current Page Number");
                        currentPage = scanner.nextInt();
                        scanner.nextLine();
                    }
                    Book book = new Book(title, author, pageCount, status, currentPage);
                    library.addBook(book);
                    storage.save(library);

                }
                case 7 -> System.out.println("Book Nook wird beendet.");
                default -> System.out.println("invalid choice!");


            }


        } while (menuChoice != 7);
        scanner.close();

    }
}
