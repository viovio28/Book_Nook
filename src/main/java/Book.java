public class Book {
    private String title;
    private String author;
    private int pageCount;
    public enum ReadingStatus{
        unread,
        read,
        ongoing
    }
    public ReadingStatus readingStatus;
    private int currentPage;


    public Book(String title, String author, int pageCount, ReadingStatus status, int currentPage){
        setTitle(title);
        setAuthor(author);
        setPageCount(pageCount);
        setReadingStatus(status);
        setCurrentPage(currentPage);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null)
            throw new IllegalArgumentException();
        this.title = title;
    }

    public String getAuthor() {
        if(author == null)
            throw new IllegalArgumentException();
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if(pageCount <= 0)
            throw new IllegalArgumentException();
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setReadingStatus(ReadingStatus status){
        this.readingStatus = status;
    }

    public void updateReadingStatus(ReadingStatus status, int currentPage){
        this.readingStatus = status;
        this.currentPage = currentPage;
    }

    public double readingProgress(){
       return (double)currentPage / pageCount * 100;
    }

    public ReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public String toString(){
        return "Title: " + getTitle() + "\nAuthor: " + getAuthor() + "\nPagecount: " + getPageCount()
                +"\nReading Status: " + getReadingStatus();
    }
}
