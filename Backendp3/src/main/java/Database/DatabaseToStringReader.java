package Database;
import java.io.IOException;
import java.io.InputStream;


public class DatabaseToStringReader {
    public static String fileReader(String filePath) {
        try (InputStream inputStream = DatabaseToStringReader.class.getClassLoader()
                .getResourceAsStream(filePath)) {

            if (inputStream == null) {
                return "File not found: " + filePath;
            }

            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            return "Error reading file: " + filePath + " - " + e.getMessage();
        }
    }

}
