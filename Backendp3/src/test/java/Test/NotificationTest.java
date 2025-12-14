package Test;

import Classes.Activity;
import Classes.Participant;
import Controller.TLSEmailSender;
import Events.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {
    FakeTLSemailSender f = new FakeTLSemailSender();
    Activity a = new Activity();
    Participant p = new Participant();
    Notification n = new Notification(a,p,f);

   @BeforeEach
   void setUp(){
       a.setActivityName("coding");
       a.setInstructors("Andreas");
       p.setFirstName("Dennis");
       p.setLastName("May");
       p.setEmail("dennismay@gmail.com");

   }

    @Test
    void testSignUp() {
       String test =  n.emailNotification("SignUp");
        assertTrue(test.contains("Hej "+ p.getFirstName()));
       assertTrue(test.contains("bekræfter hermed din tilmelding"));
    }
    @Test
    void testCanceled(){
       String test = n.emailNotification("Canceled");

       assertTrue(test.contains("bekræfter hermed din afmelding"));
    }

    @Test
    void testWaitingList(){
       String test = n.emailNotification("WaitingList");

    assertTrue(test.contains("automatisk blive tilmeldt"));

    }
    @Test
    void testEmailOnNotification(){
    n.emailNotification("SignUp");

        assertEquals(p.getEmail(), f.lastToEmail);
        assertTrue(f.lastBody.contains(a.getActivityName()));

    }
}