package Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Events.Canceled;
import Classes.Participant;
import Classes.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

class CanceledTest {

    private String testPath = "src/Server.main/sources/events";
    private static File testBaseDir;
    private Canceled canceledService;

    @BeforeEach
    void setUp() throws IOException {
        testBaseDir = new File(testPath);
        if (!testBaseDir.exists()) {
            boolean created = testBaseDir.mkdirs();
            System.out.println("Created test base directory: " + created);


        }

        canceledService = new Canceled(); // Initialize the service for each test
    }

    @AfterEach
    void tearDown() throws IOException {
      /*  if (testBaseDir.exists()) {
            for (File file : testBaseDir.listFiles()) {
                file.delete();
            }
            testBaseDir.delete();
        }*/
    }

    @Test
    void removeParticipantSuccessfully() throws IOException {
        String activityName = "Yoga test trial";
        String testJson = "{\"firstname\": \"Dennis\", \"lastname\": \"May\",\"email\":\"mail@test.dk\"}";

        // Create a mock participant and activity
        Participant participant = new Participant();

        participant.setFirstName("Dennis");
        participant.setLastName("May");
        participant.setEmail("mail@test.dk");

        Activity activity = new Activity();
        activity.setActivityName(activityName);

        File activityFile = new File(testBaseDir, activityName + "_users.txt");
        System.out.println(participant.getFirstName()+participant.getLastName()+participant.getEmail() );
        System.out.println("test" + activityFile.exists());
        // Simulate that a file exists with a participant
        Files.write(activityFile.toPath(), testJson.getBytes());

        // Ensure the file is created and contains the test participant
        assertTrue(activityFile.exists(), "File should be created for activity");
        String fileContent = Files.readString(activityFile.toPath());
        assertTrue(fileContent.contains("Dennis") &&  fileContent.contains("mail@test.dk"),"File should contain the participant");

        // remove participant from list using cancelled
      boolean test =   canceledService.removeParticipantByDetails(activityName,"Dennis","May","test@mail.dk");
         //   assertTrue(test);

        // Check if the participant is removed from the file
        String updatedContent = Files.readString(activityFile.toPath());
       //assertFalse(updatedContent.contains("Dennis") &&  updatedContent.contains("mail@test.dk"), "File should not contain the removed participant");
    }





}
