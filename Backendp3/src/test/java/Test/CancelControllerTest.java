package Test;

import Classes.Activity;
import Classes.Participant;
import Controller.CancelController;
import Controller.CancelResult;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CancelControllerTest {

    /* what do I need to setup and remove
        I need an activity to test on and I need participants assigned to that activity
        once we get the email function to work do I need for my tests to ignore sending out messages


        */


    private CancelController controller;
    private FakeTLSEmailSender fakeSender;

    private FakeActivityProvider activityProvider;
    private FakeParticipantProvider participantProvider;

    @BeforeEach
    void setUp() {

        fakeSender = new FakeTLSEmailSender();
        Activity activity = new Activity();
        activity.setActivityName("Yoga");
        activity.setActivityCapacity(2);
        activity.setActivityID(11);
        activity.setWaitingListEnabled(true);
        activityProvider = new FakeActivityProvider(activity);
        participantProvider = new FakeParticipantProvider();
        controller = new CancelController(fakeSender, activityProvider, participantProvider);
        Participant a = new Participant("A", "May", "amay@mail.com", activity.getActivityNameAndID());
        Participant b = new Participant("B", "May", "bmay@mail.com", activity.getActivityNameAndID());
        Participant c = new Participant("C", "May", "cmay@mail.com", activity.getActivityNameAndID());
        Participant d = new Participant("D", "May", "dmay@mail.com", activity.getActivityNameAndID());
        Participant e = new Participant("E", "May", "emay@mail.com", activity.getActivityNameAndID());

        participantProvider.addParticipant(a);
        participantProvider.addParticipant(b);
        participantProvider.addParticipant(c);
        participantProvider.addParticipant(d);
        participantProvider.addParticipant(e);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void activityNotFound() {

        String testJson = """
                {
                  "firstname":"A",
                  "lastname":"May",
                  "email":"amay@mail.com",
                  "activity":"DoesNotExist"
                }
                """;

        CancelResult result = controller.processCancel(testJson);

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("Aktiviteten findes ikke", result.getMessage());
        assertNull(result.getParticipant());
    }

    @Test
    void cancelConfirmedParticipant_success() {

        String json = """
                {
                  "firstname":"A",
                  "lastname":"May",
                  "email":"amay@mail.com",
                  "activity":"Yoga11"
                }
                """;

        CancelResult result = controller.processCancel(json);

        assertTrue(result.isSuccess());
    }


    @Test //A is removed and C is promoted to activity
    void promotesFirstWaitingListParticipant() {

        String json = """
                {
                  "firstname":"A",
                  "lastname":"May",
                  "email":"amay@mail.com",
                  "activity":"Yoga11"
                }
                """;

        controller.processCancel(json);
      String promotedEmail=  participantProvider.getParticipants("Yoga11").get(1).getEmail();
        assertEquals(
                "cmay@mail.com",
                promotedEmail  // because of order of operation is the last email to be send to the cancelled participant so we look for the new one at index 1
        );
    }
    @Test
    void noPromotionWhenWaitingListDisabled() {

        activityProvider.getActivity("Yoga11").setWaitingListEnabled(false);

        String json = """
        {
          "firstname":"A",
          "lastname":"May",
          "email":"amay@mail.com",
          "activity":"Yoga11"
        }
        """;

        controller.processCancel(json);

        assertNull(fakeSender.lastToEmail);
    }

}