package Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import Events.Published;
import Classes.Activity;
import org.junit.jupiter.api.Test;

class PublishedTest {
Activity testActivity = new Activity();

Published published = new Published();

    @BeforeEach
    void setUp() {

    }


    @AfterEach
    void tearDown() {
    }

    @Test //test that activity is added to list of activities
    void publish() {
    testActivity.ActivityID = 1337;
    testActivity.ActivityName = "Monner";
    testActivity.ActivityCapacity = 4;
    testActivity.TypeOfActivity = "hygge";
    testActivity.ActivityOrganizer = "Mae";
    testActivity.ActivityDescription = "Monner med Andreaz";
    testActivity.DateAndTime = 20251127;
    testActivity.GenderGroup = "girlies";
    testActivity.Instructors = "Andreaz";
    testActivity.WaitingListEnabled = true;
    testActivity.Location = "Cass";
    testActivity.ActivityDifficulty = "Hard";
    testActivity.AgeGroup = "16+";

    published.publish(testActivity);
    }
   @Test
   void publishNull(){

        published.publish(testActivity);
   }

}