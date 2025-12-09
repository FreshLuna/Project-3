package Database;

import Classes.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class GetSingularActivityFromId {

    /**
     * Returns the Activity object as JSON string for the given activityId.
     * Reuses DataLoader to ensure proper deserialization.
     *
     * @param activityId the ID of the activity to find
     * @return JSON string of the activity, or an error JSON if not found
     */
    public static String findActivityById(int activityId) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Load all activities via DataLoader
            List<Activity> activities = DataLoader.loadActivities();

            // Find the activity with the matching ID
            Optional<Activity> activityOpt = activities.stream()
                    .filter(a -> a.getActivityID() == activityId)
                    .findFirst();

            if (activityOpt.isPresent()) {
                // Convert to JSON string
                return mapper.writeValueAsString(activityOpt.get());
            } else {
                // Activity not found
                return "{\"error\": \"Activity not found\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to load activity\"}";
        }
    }
}
