package Test;

import Events.FullyBooked;
import Events.SignedUp;
import Events.Canceled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;


public class FullyBookedIntegrationTest {
SignedUp su = new SignedUp();
FullyBooked fb = new FullyBooked();
Canceled c = new Canceled();

    private static final Path TEST_DIR = Path.of("src/main/sources/events" );

    @BeforeEach
    void setUp() throws Exception{

        Files.createDirectories(TEST_DIR);

    }

    @AfterEach
    void tearDown()throws Exception {

        if (Files.exists(TEST_DIR)){
            Files.walk(TEST_DIR)
                    .map(path->path.toFile())
                    .forEach(file -> {

                        System.out.println(" debug "+file.getAbsolutePath());

                        boolean deleted =   file.delete();

                        if(!deleted){
                            System.out.println("failed to delete" +file.getAbsolutePath() );
                        }else {

                            System.out.println("delt "+ file.getAbsolutePath());
                        }

                    });
            boolean clean =  TEST_DIR.toFile().delete();
            if(!clean){
                System.out.println("failed to clean dir");
            }else {
                System.out.println("wohoo");
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
         boolean removed =    c.removeParticipant(activityName, 3);
         assertTrue(removed);

        // Now it should NOT be full anymore
        assertFalse(fb.isActivityOpen("test_temp_integration/" + activityName, 3, true));
    }

}

