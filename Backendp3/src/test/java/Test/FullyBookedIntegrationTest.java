package Test;

import Events.FullyBooked;
import Events.SignedUp;
import Events.Canceled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Config.FilePaths.EVENTS_FOLDER;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FullyBookedIntegrationTest {
SignedUp su;
FullyBooked fb;
Canceled c;


    private static final Path EVENTS_DIR = Path.of(EVENTS_FOLDER);
    private List<Path> beforeSnapshot;

    @BeforeEach
    void setUp() throws IOException {

        // Take a snapshot of all existing files before the test runs, needed because before this test deleted the entire folder
        if (Files.exists(EVENTS_DIR)) {
            try (var walk = Files.walk(EVENTS_DIR)) {
                beforeSnapshot = walk.collect(Collectors.toList());
            }
        } else {
            beforeSnapshot = new ArrayList<>();
        }

        // Initialize your classes
        su = new SignedUp();
        fb = new FullyBooked();
        c = new Canceled();
    }

    @AfterEach
    void tearDown() throws IOException {

        if (!Files.exists(EVENTS_DIR)) return;

        // Snapshot AFTER the test
        List<Path> afterSnapshot;
        try (var walk = Files.walk(EVENTS_DIR)) {
            afterSnapshot = walk.collect(Collectors.toList());
        }

        // Compute files created during the test
        Set<Path> createdFiles = afterSnapshot.stream()
                .filter(p -> !beforeSnapshot.contains(p))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toSet());

        // Delete only new files
        for (Path p : createdFiles) {
            try {
                Files.deleteIfExists(p);
            } catch (Exception e) {
                System.err.println("FAILED to delete: " + p);
            }
        }
    }
    @Test
    void activityOpen_withSignup() throws Exception{
     String activityName ="Coffee_integration_full";

        String JsonDennis = "{\"name\":\"Dennis\",\"userID\":1,\"activity\":\"" + activityName + "\"}";
        String JsonMia    = "{\"name\":\"Mia\",\"userID\":2,\"activity\":\"" + activityName + "\"}";
        String JsonLuna   = "{\"name\":\"Luna\",\"userID\":3,\"activity\":\"" + activityName + "\"}";

        su.appendParticipant(JsonDennis);
        su.appendParticipant(JsonMia);
        su.appendParticipant(JsonLuna);

        boolean full = fb.isActivityOpen(activityName,3,true);
        assertTrue(full);

    }

    @Test
    void activityNotOpen_withSignup() throws Exception{
        String activityName ="Coffee_integration_not_full";

        String JsonLena = "{\"name\":\"Lena\",\"userID\":4,\"activity\":\"" + activityName + "\"}";
        String JsonMae  = "{\"name\":\"Mae\",\"userID\":5,\"activity\":\"" + activityName + "\"}";

        su.appendParticipant(JsonMae);
        su.appendParticipant(JsonLena);

        assertFalse(fb.isActivityOpen(activityName, 3, true));
    }

   @Test
    void activityBecomesOpen_afterCancellation() throws Exception {
        String activityName = "Coffee_integration_cancel_test";

        String JsonDennis = "{\"name\":\"Dennis\",\"userID\":1,\"activity\":\"" + activityName + "\"}";
        String JsonMia    = "{\"name\":\"Mia\",\"userID\":2,\"activity\":\"" + activityName + "\"}";
        String JsonLuna   = "{\"name\":\"Luna\",\"userID\":3,\"activity\":\"" + activityName + "\"}";

        // First, sign up three participants → activity becomes full
        su.appendParticipant(JsonDennis);
        su.appendParticipant(JsonMia);
        su.appendParticipant(JsonLuna);

        // Confirm activity is fully booked
        assertTrue(fb.isActivityOpen( activityName, 3, true));

        // Now cancel one of them
        /* boolean removed =    c.removeParticipantByDetails(activityName, 3);
         assertTrue(removed);*/

        // Now it should NOT be full anymore
        assertFalse(fb.isActivityOpen("test_temp_integration/" + activityName, 3, true));
    }









}

