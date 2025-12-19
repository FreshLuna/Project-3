package Test;

import Classes.Activity;
import Classes.Participant;
import Events.FullyBooked;
import Controller.SignUpResult;
import Events.Verified;
import Controller.SignupController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupControllerTest {

    private SignupController controller;
    private FakeTLSEmailSender fakeTLSEmailSender;
    private FakeActivityProvider fakeActivityProvider;
    private FakeParticipantProvider fakeParticipantProvider;
    @BeforeEach
    void setup() {
        Verified verified = new Verified();
        FullyBooked fullyBooked = new FullyBooked();
        fakeTLSEmailSender = new FakeTLSEmailSender();
        fakeParticipantProvider = new FakeParticipantProvider();
        Activity testActivity = new Activity();
        testActivity.setActivityName("testActivity");
        testActivity.setActivityCapacity(2);
        testActivity.setActivityID(11);
        testActivity.setWaitingListEnabled(true);
        fakeActivityProvider = new FakeActivityProvider(testActivity);

        controller = new SignupController(fakeTLSEmailSender,fakeActivityProvider,fakeParticipantProvider,fullyBooked,verified);
    }

    @Test
    void testInvalidJson() {
        SignUpResult result = controller.processSignup("not-json");
        assertFalse(result.isSuccess());
        assertEquals("exception: invalid JSON format", result.getMessage());
    }

    @Test
    void testInvalidParticipantData() {
        // Verified.verifyParticipant() should return false for this
        String json = """
                {"firstname":" ","lastname":" ","dateOfBirth":" ","email":" ","tosAccept":true,"infoSendAccept":false,"activity":"testActivity11"}
                """;

        SignUpResult result = controller.processSignup(json);

        assertFalse(result.isSuccess());
        assertEquals("invalid participant data", result.getMessage());
    }

    @Test
    void signupSuccess() {
        String json = """
                {"firstname":"A","lastname":"May","dateOfBirth":"1995-02-23","email":"a@mail.com","tosAccept":true,"infoSendAccept":false,"activity":"testActivity11"}
                """;
        SignUpResult result = controller.processSignup(json);

        assertTrue(result.isSuccess());

        assertEquals(1, fakeTLSEmailSender.sentMails.size());

        FakeTLSEmailSender.Mail mail =
                fakeTLSEmailSender.sentMails.get(0);

        assertEquals("a@mail.com", mail.to);
        assertTrue(mail.body.contains("tilmelding"));
    }

    @Test
    void signupWaitingList() {
        // Fill capacity first
        fakeParticipantProvider.addParticipant(
                new Participant("A","May","a@mail.com","testActivity11"));

        fakeParticipantProvider.addParticipant(
                new Participant("B","May","b@mail.com","testActivity11"));

        String json = """
        {
          "firstname":"C",
          "lastname":"May",
          "email":"c@mail.com",
          "dateOfBirth":"1990-01-01",
          "activity":"testActivity11"
        }
        """;
        SignUpResult result = controller.processSignup(json);

        assertTrue(result.isSuccess());

        assertEquals(1, fakeTLSEmailSender.sentMails.size());

        FakeTLSEmailSender.Mail mail =
                fakeTLSEmailSender.sentMails.get(0);

        assertEquals("c@mail.com", mail.to);
    }



}


