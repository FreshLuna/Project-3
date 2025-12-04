package Test;

import Classes.Activity;
import Events.Published;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublishedTest {

    private static final File TEST_FILE = new File("src/test/resources/activities_test.json");

    @BeforeEach
    void setUp() {

        if (TEST_FILE.exists()) {
            TEST_FILE.delete();
        }
    }

    @AfterEach
    void tearDown() {

        if (TEST_FILE.exists()) {
            TEST_FILE.delete();
        }
    }

    @Test
    void publish() throws IOException {
     /*   // Arrange: create an activity
        Activity activity = new Activity();
        activity.setActivityName("Test Activity");
        activity.setActivityCapacity(5);
        activity.
        // Act: publish it
        Published.publish(activity);

        // Assert: the file exists
        assertTrue(TEST_FILE.exists(), "activities.json file should exist");

        // Assert: the file contains the activity name
        List<String> lines = Files.readAllLines(TEST_FILE.toPath());
        boolean containsActivityName = lines.stream().anyMatch(line -> line.contains("Test Activity"));
        assertTrue(containsActivityName, "activities.json should contain the published activity");

    */}
}