package Server;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Database {
  public static void main(String[] args) {
  // target file path
  File outFile = new File("Backendp3/src/Server/txtFiles/database.txt");

    // ensure parent directory exists
    File parent = outFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }

    // write with try-with-resources (auto-closes the writer)
    try (FileWriter database = new FileWriter(outFile)) {
      String json = "{\"firstname\":\"Joe\",\"lastname\":\"Mama\",\"age\":30,\"email\":\"yay@gmail.com\",\"education\":\"Aalborg Universitet\"}";
      database.write(json);
      System.out.println("Successfully wrote to the file: " + outFile.getPath());
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
