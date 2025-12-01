package Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Events.FullyBooked;
import static org.junit.jupiter.api.Assertions.*;

class FullyBookedTest {
    FullyBooked fb = new FullyBooked();
    String name = "ForTest";
    int capacity = 5;
    boolean waitlistEnabled = true;
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkActivity() {
    }

    @Test
    void isActivityFullyBookedPath() {

        boolean truePath = fb.isActivityFullyBooked(name,capacity,waitlistEnabled);

        assertTrue(truePath);

        String falseName= "ForTestFalse";

        boolean falsePath = fb.isActivityFullyBooked(falseName,capacity,waitlistEnabled);
        assertFalse(falsePath);
    }
    @Test
    void isActivityFullyBookedCapacity(){


    }
}