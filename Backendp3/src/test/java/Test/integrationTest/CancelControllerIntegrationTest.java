package Test.integrationTest;

import Classes.Activity;
import Controller.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Test.FakeTLSEmailSender;
import Test.FakeActivityProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CancelControllerIntegrationTest {
    private FakeTLSEmailSender fakeSender;
    private ParticipantRepository repo;
    private CancelController controller;
    private Path file;
    @BeforeEach
    void Setup() throws Exception {
        System.setProperty("test.env", "true");


        fakeSender = new FakeTLSEmailSender();
        Activity activity = new Activity();
        activity.setActivityName("Yoga");
        activity.setActivityID(11);
        activity.setActivityCapacity(2);
        activity.setWaitingListEnabled(true);

        ActivityProvider activityProvider = new FakeActivityProvider(activity);

        repo = new FileParticipantProvider();

        controller = new CancelController(fakeSender, activityProvider, repo);

        Path dir = Paths.get("src/test/resources/events");
        Files.createDirectories(dir);

        file = dir.resolve("Yoga11_users.txt");

        file = Paths.get("src/test/resources/events/Yoga11_users.txt");
        Files.write(file, List.of(
                json("A"),
                json("B"),
                json("C"),
                json("D")
        ));

    }
    @AfterEach
    void tearDown() throws Exception {

        Files.deleteIfExists(file);

    }
    @Test
    void integration_cancel_promotes_and_sends_emails() throws Exception {

        String cancelJson = """
        {
          "firstname":"A",
          "lastname":"May",
          "email":"a@mail.com",
          "activity":"Yoga11"
        }
        """;

        CancelResult result = controller.processCancel(cancelJson);

        assertTrue(result.isSuccess());
    // we expect emails sent to the cancelling participant, the promoted participant and the last participant that moves up on our wait list
        Assertions.assertEquals(3, fakeSender.sentMails.size());

// promoted
        Assertions.assertTrue(
                fakeSender.sentMails.stream()
                        .anyMatch(m -> m.to.equals("c@mail.com"))
        );

// canceled
        Assertions.assertTrue(
                fakeSender.sentMails.stream()
                        .anyMatch(m -> m.to.equals("a@mail.com"))
        );

// waiting list update
        Assertions.assertTrue(
                fakeSender.sentMails.stream()
                        .anyMatch(m -> m.to.equals("d@mail.com"))
        );

    }

    private String json(String name) {
        return String.format(
                "{\"firstname\":\"%s\",\"lastname\":\"May\",\"email\":\"%s@mail.com\",\"activity\":\"Yoga11\"}",
                name,
                name.toLowerCase()
        );
    }



}
