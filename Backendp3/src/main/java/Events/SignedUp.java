package Events;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SignedUp {
    public static void appendParticipant(String text) {
        try {
            int start = text.indexOf("\"activity\":\"") + 12;
            int end = text.indexOf("\"", start);
            String activity = text.substring(start, end);
            File outFile = new File("src/main/sources/events/" + activity + "_users.txt");
          /*  Obtains parent directory with outfile.getParentFile() and calls parent.mkdirs() if the
              directory does not exist.
              This creates the directory tree (txtFiles) if missing.
            */
            File parent = outFile.getParentFile();
            if (parent != null && !parent.exists()) {
                boolean success = parent.mkdirs();
                System.out.println("files created " + success);
            }

          /*  Here it opens a FileWriter in append mode
              If the file does not exist, FileWriter will create it.
              The true flag makes the writer append to the existing file instead of trimming it
            */
            try (FileWriter database = new FileWriter(outFile, true)) { // append mode
                database.write(text); //Here it writes the provided text from PostHandler.java
                database.write(System.lineSeparator()); //Writes a newline using System.lineSeparator()
                System.out.println("Appended participant to: " + outFile.getPath());
            } catch (IOException e) {
                System.out.println("An error occurred while writing participant.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
