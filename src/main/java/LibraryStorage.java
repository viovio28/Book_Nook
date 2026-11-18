import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibraryStorage {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path file = Path.of("book.json");

    public void save(Library library) throws IOException {
        String json = gson.toJson(library);
        Files.writeString(file,json);
    }

    public Library load() throws IOException{
        if (!Files.exists(file)){
            return new Library();
        }
        String json = Files.readString(file);
        return gson.fromJson(json, Library.class);
    }
}
