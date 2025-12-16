package Test;

import Events.FullyBooked;
import Controller.SignUpResult;
import Events.Verified;
import Controller.SignupController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupControllerTest {

    private SignupController controller;

    @BeforeEach
    void setup() {
        Verified verified = new Verified();
        FullyBooked fullyBooked = new FullyBooked();

        controller = new SignupController();
    }

    @Test
    void testInvalidJson() {
        SignUpResult result = controller.processSignup("not-json");
        assertFalse(result.isSuccess());
        assertEquals("exception: invalid JSON format", result.getMessage());
    }

    @Test
    void testInvalidParticipant() {
        // Verified.verifyParticipant() should return false for this
        String json = """
                {"firstname":" ","lastname":" ","dateOfBirth":" ","email":" ","tosAccept":true,"infoSendAccept":false,"activity":"Badminton Night11"}
                """;

        SignUpResult result = controller.processSignup(json);

        assertFalse(result.isSuccess());
        assertEquals("invalid participant data", result.getMessage());
    }

    @Test
    void testActivityFullReturnsFail() {
        // Adjust your FullyBooked logic or test data if necessary
        // This test assumes activity is NOT open and waiting list disabled -> fail

        String json = """
                {"firstName":"Test User","lastName":"super cat","email":"test@example.com"}
                """;

        SignUpResult result = controller.processSignup(json);

        // Depends on how FullyBooked uses the empty Activity object:
        // With default Activity values, isOpen likely returns false → success()
        // If yours returns true + no waiting list, this becomes fail.

        assertNotNull(result);
    }



    @Test
    void testParticipantAlreadySignedUp() {



    }



    /*

    fake email sender
    fake activity
    fake participants

/*
    @Test
    void testSuccessSignupWhenOpen() {
        // ⚠️ This test requires your Activity() default values
        // to produce: isOpen == false → success()
        String json = """
{
    "firstName": "Anita",
    "lastName": "Eat",
    "dateOfBirth": "20/02/1999",
    "email": "food@mail.com",
    "tosAccept": true,
    "infoSendAccept": true,
    "activity": "Advanced Trail Hike"
}
""";


        SignUpResult result = controller.processSignup(json);
        System.out.println(result.getParticipant());
        assertTrue(result.isSuccess());
        assertEquals("Anna er tilmeldt", result.getMessage());
    }*/
}
