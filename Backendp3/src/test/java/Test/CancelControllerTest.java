package Test;

import Controller.CancelController;
import Controller.CancelResult;
import Model.CancelRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        controller = new CancelController();
        fakeSender = new FakeTLSEmailSender();
        //controller.setEmailSender(fakeSender);


    }

    @AfterEach
    void tearDown() {
    }

   /* @Test
    void testProcessCancelParticipantRemoved() throws Exception {
        CancelRequest request = new CancelRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setActivity("Yoga101");

        String json = mapper.writeValueAsString(request);

        CancelResult result = controller.processCancel(json);

        assertNotNull(fakeSender.lastToEmail);
        assertEquals("john@example.com", fakeSender.lastToEmail);
        assertEquals("Yoga101", fakeSender.lastActivity);
    }

    */

    @Test
    void test2(){}


   /* @Test
    void testCancelWaitingListParticipant() throws Exception {
        CancelRequest request = new CancelRequest();
        request.setFirstName("Lena");
        request.setLastName("Hayes");
        request.setEmail("lena.shayes@gmail.com");
        request.setActivity("Badminton Night19");

        String json = mapper.writeValueAsString(request);

        CancelResult result = controller.processCancel(json);

        //assertTrue(result.success());

    }


    */




    @Test

    void test(){}
}