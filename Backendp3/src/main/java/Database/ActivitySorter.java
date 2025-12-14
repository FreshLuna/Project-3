package Database;

import Classes.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static Config.FilePaths.EVENTS_FOLDER;

public class ActivitySorter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static int activityCount(Activity a) throws Exception {
        Path filePath = Paths.get(EVENTS_FOLDER + a.getActivityNameAndID() + "_users.txt");

        int participantCount = 0;
        if (Files.exists(filePath)) {
            List<String> users = Files.readAllLines(filePath);
            participantCount = users.size();
        }
        return participantCount;
    }
    public static String getNewActivities(List<Activity> activities) throws Exception {
        List<Activity> sorted = new ArrayList<>(activities);
        Collections.reverse(activities);
        ArrayNode enriched = extracted(sorted);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(enriched);}

    public static String getPopularActivities(List<Activity> activities) throws Exception {
        List<Activity> sorted = new ArrayList<>(activities);

        // Sort remaining activities by participant count descending
        activities.sort((a1, a2) -> {
            int count1;
            int count2;
            try {
                count1 = activityCount(a1);
                count2 = activityCount(a2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Integer.compare(count2, count1); // descending order
        });
        ArrayNode enriched = extracted(sorted);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(enriched);
    }

    public static String getUpcomingActivities(List<Activity> activities) throws Exception {
        List<Activity> sorted = new ArrayList<>(activities);

        activities.sort((a1, a2) -> {
            long count1;
            long count2;
            try {
                count1 = a1.getDateAndTime();
                count2 = a2.getDateAndTime();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Long.compare(count1, count2);// descending order reverse for oldest
        });
        ArrayNode enriched = extracted(sorted);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(enriched);
    }

    private static ArrayNode extracted(List<Activity> activities) throws Exception {
        ArrayNode array = mapper.createArrayNode();

        for (Activity activity : activities) {
            int max = activity.getActivityCapacity();
            int current = activityCount(activity);
            int remaining = max-current;

            // convert Activity → JSON tree
            ObjectNode activityNode = mapper.valueToTree(activity);

            // inject frontend-only field
            activityNode.put("ParticipantCount", remaining);

            array.add(activityNode);
        }
        return array;
    }
}