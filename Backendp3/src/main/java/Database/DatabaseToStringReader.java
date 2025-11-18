package Database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class DatabaseToStringReader {
    // public static String readFileToString(String path) throws IOException {
    //     System.out.println("test");

    //     return Files.readString(Path.of(path));
    // }

    // new way to do it?
    public static String readFileToString(String filename) throws IOException {
        try (var stream = DatabaseToStringReader.class.getClassLoader().getResourceAsStream(filename)) {
            if (stream == null) {
                throw new IOException("File not found: " + filename);
            }
            return new String(stream.readAllBytes());
        }
    }
}
