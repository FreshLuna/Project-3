package Test;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityDeserializationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testDeserializeActivityList_StandaloneJSON() throws Exception {

        String testJson = """
        [
          {
            "ActivityID": 101,
            "ActivityName": "Test Yoga",
            "ActivityOrganizer": "TestOrg",
            "TypeOfActivity": "Yoga",
            "Instructors": "Instructor A",
            "DateAndTime": 202501011200,
            "Location": "Test Room A",
            "GenderGroup": "All",
            "AgeGroup": "18+",
            "ActivityCapacity": 2,
            "ActivityDescription": "Testing activity.",
            "ActivityDifficulty": "Easy",
            "WaitingListEnabled": true,
            "WaitingListCapacity": 1,
            "tags": ["test", "sample"]
          },
          {
            "ActivityID": 102,
            "ActivityName": "Test Climbing",
            "ActivityOrganizer": "TestOrg2",
            "TypeOfActivity": "Climbing",
            "Instructors": "Instructor B",
            "DateAndTime": 202501021200,
            "Location": "Test Gym",
            "GenderGroup": "All",
            "AgeGroup": "12+",
            "ActivityCapacity": 1,
            "ActivityDescription": "Testing climbing.",
            "ActivityDifficulty": "Medium",
            "WaitingListEnabled": false,
            "WaitingListCapacity": 0,
            "tags": ["test", "sample2"]
          },
          {
            "ActivityID": 103,
            "ActivityName": "Test Kayaking",
            "ActivityOrganizer": "TestOrg3",
            "TypeOfActivity": "Kayaking",
            "Instructors": "Instructor C",
            "DateAndTime": 202501031200,
            "Location": "Test Dock",
            "GenderGroup": "All",
            "AgeGroup": "18+",
            "ActivityCapacity": 3,
            "ActivityDescription": "Testing kayaking.",
            "ActivityDifficulty": "Hard",
            "WaitingListEnabled": true,
            "WaitingListCapacity": 2,
            "tags": ["water"]
          }
        ]
        """;

        List<Activity> activities =
                mapper.readValue(testJson, new TypeReference<List<Activity>>() {});

        assertEquals(3, activities.size());

        Activity yoga = activities.get(0);
        System.out.println(yoga);
        assertEquals(101, yoga.getActivityID());
        assertEquals("Test Yoga", yoga.getActivityName());
        assertTrue(yoga.getWaitingListEnabled());
        assertEquals(1, yoga.getWaitingListCapacity());

        Activity climb = activities.get(1);
        assertFalse(climb.getWaitingListEnabled());
        assertEquals(0, climb.getWaitingListCapacity());
    }
}
