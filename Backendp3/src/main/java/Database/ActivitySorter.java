package Database;

import Classes.Activity;
import Events.Expired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import static Database.DataLoader.loadActivities;
import static Events.FullyBooked.isActivityOpen;

public class ActivitySorter {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static int activityCount(Activity a) throws Exception {
        Path filePath = Paths.get("src/main/sources/events/" + a.getActivityName() + "_users.txt");

        int participantCount = 0;
        if (Files.exists(filePath)) {
            List<String> users = Files.readAllLines(filePath);
            participantCount = users.size();
        }
        return participantCount;
    }
    public static String getNewActivities() throws Exception {
        Expired manager = new Expired();
        manager.removeExpiredActivities();
        List<Activity> activities = loadActivities();
        return Collections.unmodifiableList(activities).toString();
    }

    public static String getPopularActivities() throws Exception {
        Expired manager = new Expired();
        manager.removeExpiredActivities();
        List<Activity> activities = new ArrayList<>(loadActivities()); // make mutable

        // remove full activities
        activities.removeIf(a -> !isActivityOpen(a.getActivityName(), a.getActivityCapacity(), a.getWaitingListEnabled()));

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

    public static String getUpcomingActivities() throws Exception {
        Expired manager = new Expired();
        manager.removeExpiredActivities();
        List<Activity> activities = new ArrayList<>(loadActivities()); // make mutable
        activities.removeIf(a -> isActivityOpen(a.getActivityName(), a.getActivityCapacity(), a.getWaitingListEnabled()));
        Collections.reverse(activities);
        return Collections.unmodifiableList(activities).toString();       // return as unmodifiable
    }
}


