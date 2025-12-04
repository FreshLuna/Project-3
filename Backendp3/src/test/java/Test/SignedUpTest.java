package Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Events.SignedUp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class SignedUpTest {

  private String testPath = "src/main/sources/events/test_temp";

 private static File testBaseDir ;

    @BeforeEach
    void setUp() throws IOException {
        testBaseDir = new File(testPath);
        if(!testBaseDir.exists()){
            boolean created = testBaseDir.mkdirs();
            System.out.println("Created test based directory" +created );
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if(testBaseDir.exists()){
            for (File file: testBaseDir.listFiles()){
                file.delete();
            }
            testBaseDir.delete();
        }
    }

    @Test
    void appendParticipant()throws IOException {

        String activity = "Coffee";

        String testJson="{\"name\":\"Mia\",\"activity\":\"test_temp/Coffee\"}";

        File testFile = new File(testBaseDir, activity + "_users.txt");

        String originalPath = "src/main/sources/events/";
        File activityDir = new File(originalPath + "test_temp");
        if (!activityDir.exists()) activityDir.mkdirs();


        SignedUp.appendParticipant(testJson);

        //test that file is created
        File outFile = new File(testPath+ "/Coffee_users.txt");
        assertTrue(outFile.exists(),"File should be created");

        String content = Files.readString(outFile.toPath());
        assertTrue(content.contains("Mia"));
    }


}