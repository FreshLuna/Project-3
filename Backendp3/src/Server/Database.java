package Server;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Database {
  public static void main(String[] args) {
  // target file path
    // This main is left as a convenience for manual testing.
    System.out.println("Database utility - no action performed in main(). Use appendParticipant() from server handlers.");
  }

  // Appends the given text (typically a JSON string) as a new line to participants.txt
  public static void appendParticipant(String text) {
    File outFile = new File("src/Server/txtFiles/participants.txt");

    /**  Obtains parentdirectory with outfile.getParentFile() and calls parent.mkdirs() if the
     * directory does not exist.
     * This creates the directory tree (txtFiles) if missing.
     * */
    File parent = outFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }

    /**Here it opens a FileWriter in append mode
     * If the file does not exist, FileWriter will create it.
     * The true flag makes the writer append to the existing file instead of trimming it
     */
    try (FileWriter database = new FileWriter(outFile, true)) { // append mode
      if (text == null) text = "";
      database.write(text); //Here it writes the provided text from PostHandler.java
      database.write(System.lineSeparator()); //Writes a newline using System.lineSeparator()
      System.out.println("Appended participant to: " + outFile.getPath());
    } catch (IOException e) {
      System.out.println("An error occurred while writing participant.");
      e.printStackTrace();
    }
  }
}
