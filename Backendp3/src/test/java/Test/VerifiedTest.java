package Test;
import Events.Verified;
import Classes.Activity;
import Classes.Participant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VerifiedTest {

    private final Verified verifier = new Verified();

    // testing verified on participants
    @Test
    void testVerifyParticipantValid() {
        Participant p = new Participant();
        p.setFirstName("  Dennis123  ");
        p.setLastName("Kat!!");
        p.setEmail(" D.Kat@Test.dk ");
        p.setDateOfBirth("01/01/1985");

        boolean result = verifier.verifyParticipant(p);

        assertTrue(result);
        assertEquals("Dennis", p.getFirstName());
        assertEquals("Kat", p.getLastName());
        assertEquals("d.kat@test.dk", p.getEmail());
        assertEquals("01/01/1985", p.getDateOfBirth());
    }

    @Test
    void testParticipantInvalidEmail() {
        Participant p = new Participant();
        p.setFirstName("Test");
        p.setLastName("Kat");
        p.setEmail("not-an-email");
        p.setDateOfBirth("01/01/1985");

        assertFalse(verifier.verifyParticipant(p));
    }

    @Test
    void testParticipantInvalidDOB() {
        Participant p = new Participant();
        p.setFirstName("Test");
        p.setLastName("Kat");
        p.setEmail("T.Kat@Test.dk");
        p.setDateOfBirth("1985-01-01"); // wrong format

        assertFalse(verifier.verifyParticipant(p));
    }

    @Test
    void testParticipantNameCleaningResultingInInvalid() {
        Participant p = new Participant();
        p.setFirstName("12345"); // cleaned → "" → null
        p.setLastName("Kat");
        p.setEmail("T.Kat@Test.dk");
        p.setDateOfBirth("01/01/1985");

        assertFalse(verifier.verifyParticipant(p));
        assertNull(p.getFirstName());
    }
    // test section for verify on activities
    @Test
    void testVerifyActivityValid() {
        Activity a = new Activity();
        a.setActivityName("  Monner / Hard ");
        a.setActivityCapacity(10);
        a.setLocation("   Cass ");
        a.setAgeGroup("20+ years");

        boolean result = verifier.verifyActivity(a);

        assertTrue(result);
        assertEquals("monner--hard", a.getActivityName());
        assertEquals("Cass", a.getLocation());
        assertEquals("20+", a.getAgeGroup());
    }

    @Test
    void testActivityInvalidCapacity() {
        Activity a = new Activity();
        a.setActivityName("Running");
        a.setActivityCapacity(0);
        a.setLocation("Havnen");
        a.setAgeGroup("18+");

        assertFalse(verifier.verifyActivity(a));
    }

    @Test
    void testActivityInvalidAgeGroup() {
        Activity a = new Activity();
        a.setActivityName("Running");
        a.setActivityCapacity(10);
        a.setLocation("Havnen");
        a.setAgeGroup("18 years");

        assertFalse(verifier.verifyActivity(a));
    }

    @Test
    void testActivityMissingNameAfterCleaning() {
        Activity a = new Activity();
        a.setActivityName("!!!");
        a.setActivityCapacity(10);
        a.setLocation("Havnen");
        a.setAgeGroup("18+");

        assertFalse(verifier.verifyActivity(a));
        assertEquals("", a.getActivityName());
    }
}
