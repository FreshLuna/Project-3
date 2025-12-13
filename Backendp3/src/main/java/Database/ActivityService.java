package Database;

import Classes.Activity;
import Classes.Filter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static List<Activity> getAllActivities() throws Exception {
        List<Activity> all = new ArrayList<>(activities);  // fresh copy thats modifiable
        System.out.println(activities);
        System.out.println("before");

        all.removeIf(a -> isActivityOpen(a.getActivityNameAndID(), a.getActivityCapacity(), a.getWaitingListEnabled()));
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
    private static List<Activity> filterByField(List<String> values, String field) {
        if (values == null || values.isEmpty()) return null;

        Map<String, List<Activity>> fieldIndex = indexes.get(field);
        Set<Activity> resultSet = new LinkedHashSet<>(); // preserves order
        for (String value : values) {
            List<Activity> matches = fieldIndex.getOrDefault(value, Collections.emptyList());
            resultSet.addAll(matches);
        }
        return new ArrayList<>(resultSet);
    }


    private static List<Activity> filterActivities(Filter filter) {

        Set<Activity> result = new LinkedHashSet<>(activities);

        for (String field : fieldGetters.keySet()) {
            List<String> selected = filter.getFieldValues(field);
            if (selected == null || selected.isEmpty()) continue;

            List<Activity> fieldMatches = filterByField(selected, field);
            result.retainAll(fieldMatches);
        }

        return new ArrayList<>(result);
    }

    public static String filterFromJson2(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Filter filter = mapper.readValue(body, Filter.class);

        ActivityService service = new ActivityService(); // builds indexes
        List<Activity> filtered = filterActivities(filter);
        return (mapper.writeValueAsString(filtered));
    }
}