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

    private String testPath = "src/main/sources/events";
    private static File testBaseDir;
    private Canceled canceledService;
    private ByteArrayOutputStream outputStreamCaptor; //needed to test string on remove when not found

    @BeforeEach
    void setUp() throws IOException {
        testBaseDir = new File(testPath);
        if (!testBaseDir.exists()) {
            boolean created = testBaseDir.mkdirs();
            System.out.println("Created test base directory: " + created);


        }
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
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
        String testJson = "{\"userID\":1337, \"name\": \"Dennis\"}";

        // Create a mock participant and activity
        Participant participant = new Participant();

        participant.setFirstName("Dennis");
        participant.setUserID(1337);

        Activity activity = new Activity();
        activity.setActivityName(activityName);

        File activityFile = new File(testBaseDir, activityName + "_users.txt");
        System.out.println(participant.getFirstName()+participant.getUserID() );
        System.out.println("blah" + activityFile.exists());
        // Simulate that a file exists with a participant
        Files.write(activityFile.toPath(), testJson.getBytes());

        // Ensure the file is created and contains the test participant
        assertTrue(activityFile.exists(), "File should be created for activity");
        String fileContent = Files.readString(activityFile.toPath());
        assertTrue(fileContent.contains("Dennis"), "File should contain the participant");

        // Perform the removal operation

        canceledService.checkParticipant(activity, participant);

        // Check if the participant is removed from the file
        String updatedContent = Files.readString(activityFile.toPath());
       assertFalse(updatedContent.contains("Dennis"), "File should not contain the removed participant");
    }

    @Test
    void removeParticipantWhenFileDoesNotExist() {
        String activityName = "Running";
        Participant participant = new Participant();
        participant.setUserID(456);
        participant.setFirstName("Lena");
        Activity activity = new Activity();
        activity.setActivityName(activityName);


        // Check for the file that doesn't exist
        File activityFile = new File(testBaseDir, activityName + "_users.txt");

        assertFalse(activityFile.exists(), "File should not exist before any operation");

        // Try to remove the participant
        boolean result = canceledService.removeParticipant(activityName, participant.getUserID());

        // Verify that the operation returns false (file doesn't exist)
        assertFalse(result, "Should return false if the file doesn't exist");
    }

    @Test
    void removeParticipantWhenNotFound() throws IOException {
        String activityName = "Dance";
        Participant participant = new Participant();
        participant.setFirstName("Mae");
        participant.setUserID(159);
        Activity activity = new Activity();
        activity.setActivityName(activityName);

        // Create a file but with a different participant
        File activityFile = new File(testBaseDir, activityName + "_users.txt");
        Files.write(activityFile.toPath(), "{\"userID\":101, \"name\": \"Cat\"}".getBytes());

        // Check if the file contains the wrong participant
        String initialContent = Files.readString(activityFile.toPath());
        assertTrue(initialContent.contains("Cat"), "File should contain another participant");

        // Try to remove a participant that isn't in the file
        canceledService.checkParticipant(activity, participant);

        // Make sure we get the right if on check
        String expected = "Participant " + participant.getUserID() + " was NOT found in: "+activity.getActivityName();
        assertTrue(outputStreamCaptor.toString().contains(expected), "Expected message was not printed");

        // The content should remain the same since the participant wasn't found
        String finalContent = Files.readString(activityFile.toPath());
        assertTrue(finalContent.contains("Cat"), "File content should not change if the participant wasn't found");
    }
}
