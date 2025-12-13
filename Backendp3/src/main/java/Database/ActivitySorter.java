package Database;

import Classes.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import static Config.FilePaths.EVENTS_FOLDER;

public class ActivitySorter {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static int activityCount(Activity a) throws Exception {
        Path filePath = Paths.get(EVENTS_FOLDER + a.getActivityName() + "_users.txt");

        int participantCount = 0;
        if (Files.exists(filePath)) {
            List<String> users = Files.readAllLines(filePath);
            participantCount = users.size();
        }
        return participantCount;
    }
    public static String getNewActivities(List<Activity> activities) {
        Collections.reverse(activities);
        return activities.toString();
    }

    public static String getPopularActivities(List<Activity> activities) throws Exception {

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
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(activities);
    }

    public static String getUpcomingActivities(List<Activity> activities) throws Exception {
        return activities.toString();
    }
}


