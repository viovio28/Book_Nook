import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public List<Book> getBooks(){
        return new ArrayList<>(books);
    }

    public Library(){
        books = new ArrayList<>();
    }

    public void addBook(Book newBook){
        books.add(newBook);
    }

    public void removeBook(Book book){
        books.remove(book);
    }

    public Book getBookByTitle(String title){
        for(Book b : books){
            if(b.toString().startsWith("Title: " + title)){
                return b;
            }
        }
        return null;
    }

    public Book getBookByAuthor(String author){
        for(Book b : books){
            if(b.getAuthor().startsWith(author)){
                return b;
            }
        }
        return null;
    }

    public Book getBookByStatus(Book.ReadingStatus status){
        for(Book b: books){
            if(b.readingStatus.equals(status)){
                System.out.println(b);

            }
        }
        return null;
    }

    public void getAllBooks(){
        for(Book b : books){
            System.out.println(b);
        }
    }

}
