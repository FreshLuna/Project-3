package Test;

import Events.FullyBooked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

class FullyBookedTest {

    private static final Path TEST_DIR = Path.of("src/main/sources/events/test_temp");


    @BeforeEach
    void setUp() throws Exception {
        Files.createDirectories(TEST_DIR);


    }

    @AfterEach
    void tearDown()throws Exception {

        if (Files.exists(TEST_DIR)){
            Files.walk(TEST_DIR)
                    .map(path->path.toFile())
                    .forEach(file -> file.delete());
            TEST_DIR.toFile().delete();
        }
    }

    @Test
    void activityOpen() throws Exception{
        String activityName = "Coffee_full";
        Path file = TEST_DIR.resolve(activityName +"_users.txt");
        Files.writeString(file, "Mia\nLuna\nDennisMay\n");

        assertTrue(FullyBooked.isActivityOpen("test_temp/" + activityName, 3, false));
    }

    @Test
    void ActivityNotOpen()throws Exception {

        String activityName = "Coffee_Not_full";
        Path file = TEST_DIR.resolve(activityName +"_users.txt");
        Files.writeString(file,"Mae\nLena\n");
        assertFalse(FullyBooked.isActivityOpen("test_temp/" + activityName,3,false));

    }



    }
