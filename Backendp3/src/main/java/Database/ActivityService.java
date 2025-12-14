package Database;

import Classes.Activity;

import java.util.*;
import java.util.function.Function;

import static Classes.Filter.fieldExtractors;
import static Classes.Filter.fieldGetters;
import static Events.FullyBooked.isActivityOpen;

//
//    This shit works but is complicated we can use this or keep using the old stuff.
//

public class ActivityService {


    private static List<Activity> activities;
    private static final Map<Integer, Activity> idIndex = new HashMap<>();
    private static final Map<String, Map<String, List<Activity>>> indexes = new HashMap<>();

    //field extractors are defined in filters and used to generate indexes.

    // No filepath needed now
    public ActivityService() {
        activities = DataLoader.loadActivities(); // uses classpath
        buildIndexes();
    }

    public static List<Activity> getActivities() {
        return activities;
    }

    public static Map<String, Map<String, List<Activity>>> getIndexes() {
        return indexes;
    }

    public static List<Activity> getAllActivities() {
        List<Activity> all = new ArrayList<>(activities);  // fresh copy thats modifiable
        all.removeIf(a -> isActivityOpen(a.getActivityNameAndID(), a.getActivityCapacity(), a.getWaitingListEnabled())
                && !a.getWaitingListEnabled());
        return all;
    }


    public static Activity getActivityById(int id) {
        return idIndex.get(id);
    }

    private void buildIndexes() {
        // Initialize all field indexes
        for (String field : fieldGetters.keySet()) {
            indexes.put(field, new HashMap<>());
        }

        // Populate indexes
        for (Activity activity : activities) {
            for (Map.Entry<String, Function<Activity, List<String>>> entry : fieldExtractors.entrySet()) {
                String field = entry.getKey();
                List<String> values = entry.getValue().apply(activity);

                for (String value : values) {
                    indexes.get(field).computeIfAbsent(value, k -> new ArrayList<>()).add(activity);
                }
            }
            idIndex.put(activity.getActivityID(), activity);
        }
    }
    // filtering method
}