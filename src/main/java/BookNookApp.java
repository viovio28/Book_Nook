import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;


public class BookNookApp extends Application {
    private final LibraryStorage storage = new LibraryStorage();
    private Library library;

    @Override
    public void start(Stage primaryStage) throws IOException {
        library = storage.load();
        Label heading = new Label("Your personal little Book Nook");

        ListView<Book> bookList = new ListView<>();
        bookList.getItems().setAll(library.getBooks());

        Button addButton = new Button("Add new Book");
            addButton.setOnAction(event -> showAddBookWindow(primaryStage, bookList));


        VBox layout = new VBox(20, heading, bookList, addButton );

        Scene scene = new Scene(layout, 800, 600);

        primaryStage.setTitle("Book Nook");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddBookWindow(Stage owner, ListView<Book> bookList){
       Stage popup = new Stage();
       popup.setTitle("Add new Book");
       popup.initOwner(owner);
       popup.initModality(Modality.WINDOW_MODAL);

        TextField titleInput = new TextField();
        titleInput.setPromptText("Enter Book Title:");

        TextField authorInput = new TextField();
        authorInput.setPromptText("Enter Author name:");

        TextField pageCountInput = new TextField();
        pageCountInput.setPromptText("Enter Pagecount");

        RadioButton unreadButton = new RadioButton("Unread");
        RadioButton readButton = new RadioButton("read");
        RadioButton ongoingButton = new RadioButton("Currently reading");

        ToggleGroup statusGroup = new ToggleGroup();
        unreadButton.setToggleGroup(statusGroup);
        readButton.setToggleGroup(statusGroup);
        ongoingButton.setToggleGroup(statusGroup);

        unreadButton.setSelected(true);

        unreadButton.setUserData(Book.ReadingStatus.unread);
        readButton.setUserData(Book.ReadingStatus.read);
        ongoingButton.setUserData(Book.ReadingStatus.ongoing);

        HBox statusOptions = new HBox(
                10, unreadButton, readButton, ongoingButton
        );

        TextField currentPage = new TextField();
        currentPage.setPromptText("Enter current Page Number");

        currentPage.setVisible(false);
        currentPage.setManaged(false);

        statusGroup.selectedToggleProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    boolean ongoing = ongoingButton.isSelected();

                    currentPage.setVisible(ongoing);
                    currentPage.setManaged(ongoing);
                }
        );

        Label feedback = new Label();
        Button saveButton = new Button("Save Book");

        saveButton.setOnAction(event -> {
            String title = titleInput.getText().trim();
            String author = authorInput.getText().trim();

            if(title.isBlank() || author.isBlank()){
                feedback.setText("Please enter title and author.");
                return;
            }

            int pageCount = Integer.parseInt(pageCountInput.getText().trim());

            Book.ReadingStatus status = (Book.ReadingStatus) statusGroup.getSelectedToggle().getUserData();
            int actualPage = 0;

            if(status == Book.ReadingStatus.read) {
                actualPage = pageCount;
            }else if(status == Book.ReadingStatus.ongoing){
                actualPage = Integer.parseInt(currentPage.getText().trim());
            }

            if(pageCount <= 0 || actualPage < 0 || actualPage > pageCount){
                feedback.setText("Please check page numbers.");
                return;
            }

            Book book = new Book(title, author, pageCount, status, actualPage);
            library.addBook(book);
            try {
                storage.save(library);
            } catch (IOException e) {
                library.removeBook(book);
                feedback.setText("Saving failed. Please try again.");
                return;
            }

            bookList.getItems().setAll(library.getBooks());
            popup.close();
        });

        VBox form = new VBox(
                10, titleInput, authorInput, pageCountInput,
                statusOptions, currentPage, feedback, saveButton
        );

        form.setPadding(new Insets(20));

        popup.setScene(new Scene(form, 450, 350));
        popup.show();


    }

    private void showBookDetails(Stage owner, Book book){

    }

    public static void main(String[] args){
        launch(args);
    }
}
