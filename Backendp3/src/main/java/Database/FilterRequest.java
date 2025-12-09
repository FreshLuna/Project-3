package Database;

import Classes.Activity;
import Classes.Filter;
import Database.FilterActivities;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class FilterRequest {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Activity> filterFromJson(
            List<Activity> activities,
            String jsonFilter
    ) {
        try {
            // Convert JSON string to Filter object
            Filter filter = mapper.readValue(jsonFilter, Filter.class);

            // Filter activities
            return FilterActivities.filterActivities(activities, filter);

        } catch (Exception e) {
            e.printStackTrace();
            // In case of error, return original list
            return activities;
        }
    }
}
