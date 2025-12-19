package Test.integrationTest;

import Classes.Activity;
import Controller.*;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import Test.FakeTLSEmailSender;
import Test.FakeActivityProvider;
import Test.FakeParticipantProvider;
import static org.junit.jupiter.api.Assertions.*;

class SignupControllerIntegrationTest {

    private SignupController controller;
    private FakeTLSEmailSender fakeSender;
    private Path userFile;

    @BeforeEach
    void setup() throws Exception {

        System.setProperty("test.env", "true");

        fakeSender = new FakeTLSEmailSender();

        Activity activity = new Activity();
        activity.setActivityName("Yoga");
        activity.setActivityID(11);
        activity.setActivityCapacity(2);
        activity.setWaitingListEnabled(true);

        ActivityProvider activityProvider = new FakeActivityProvider(activity);
        ParticipantRepository repo = new FileParticipantProvider();

        controller = new SignupController(fakeSender, activityProvider, repo,new Events.FullyBooked(),
                new Events.Verified()
        );

        userFile = Paths.get("src/test/resources/events/Yoga11_users.txt");
        Files.createDirectories(userFile.getParent());
        Files.deleteIfExists(userFile);
        Files.createFile(userFile);
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(userFile);
    }

    @Test
    void participantAlreadySignedUp_integration() {

        String signupJson = """
            {
              "firstname":"A",
              "lastname":"May",
              "email":"a@mail.com",
              "dateOfBirth":"1990-01-01",
              "activity":"Yoga11"
            }
            """;

        SignUpResult first = controller.processSignup(signupJson);
        assertTrue(first.isSuccess());

        SignUpResult second = controller.processSignup(signupJson);
        assertFalse(second.isSuccess());
        assertEquals("participant already signed up", second.getMessage());

        List<String> lines = readFile();
        assertEquals(1, lines.size());
    }

    private List<String> readFile() {
        try {
            return Files.readAllLines(userFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
