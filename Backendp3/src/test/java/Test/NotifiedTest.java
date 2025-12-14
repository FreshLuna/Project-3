package Test;

import Classes.Activity;
import Classes.Participant;
import Events.Notified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotifiedTest {
    FakeTLSemailSender f = new FakeTLSemailSender();
    Activity a = new Activity();
    Participant p = new Participant();
    Notified n = new Notified(a,p,f);

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
    @Test
    void LenaTest() {
        Activity activity = new Activity();
        activity.setActivityName("TestActivity");
        activity.setActivityCapacity(2);
        activity.setWaitingListEnabled(true);

        List<Participant> participants = List.of(
                new Participant("Alice","erte","Alice@mail.com",activity.getActivityName()),
                new Participant("Bob", "Marley", "bob@gmail.com", activity.getActivityName()),
                new Participant("Charlie","Darwin","Cdaw@mail.com",activity.getActivityName()),
                new Participant("Diana","Princess", "princess@royal.uk",activity.getActivityName()),
                new Participant("Eve","Adams","Eve@adman.com",activity.getActivityName() )
        );

        int capacity = activity.getActivityCapacity();

        List<Participant> top3 = participants.subList(
                capacity,
                Math.min(capacity + 3, participants.size())
        );

        System.out.println("Capacity: " + capacity);
        System.out.println("Participants:");
        participants.forEach(p -> System.out.println("- " + p.getFirstName()));

        System.out.println("\nTop 3 waiting list:");

        for (int i = 0; i < top3.size(); i++) {
            System.out.println((i + 1) + ": " + top3.get(i).getFirstName());
        }

    }

}