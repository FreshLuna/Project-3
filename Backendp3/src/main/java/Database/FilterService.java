package Database;
import Classes.Activity;
import Classes.Filter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import static Classes.Filter.fieldGetters;
import static Database.ActivitySorter.getUpcomingActivities;
import static Events.FullyBooked.isActivityOpen;

//
//    This shit works but is complicated we can use this or keep using the old stuff.
//


public class FilterService {
    // filtering method
    private static List<Activity> filterByField(List<String> values, String field) {
        if (values == null || values.isEmpty()) return null;

        if (Objects.equals(field, "tags")) {
            if (values.contains("Solsikke")) {
                // Return only activities that include the "Solsikke" tag
                List<Activity> result = new ArrayList<>();
                for (Activity a : ActivityService.getActivities()) {
                    if (a.getTags().contains("Solsikke")) {
                        result.add(a);
                    }
                }
                return result;
            }
        }

        Map<String, List<Activity>> fieldIndex = ActivityService.getIndexes().get(field);
        Set<Activity> resultSet = new LinkedHashSet<>();
        for (String value : values) {
            List<Activity> matches = fieldIndex.getOrDefault(value, Collections.emptyList());
            resultSet.addAll(matches);
        }

        return new ArrayList<>(resultSet);
    }

    private static List<Activity> filterActivities(Filter filter) {

        Set<Activity> result = new LinkedHashSet<>(ActivityService.getAllActivities());

        for (String field : fieldGetters.keySet()) {
            List<String> selected = filter.getFieldValues(field);
            if (selected == null || selected.isEmpty()) continue;

            List<Activity> fieldMatches = filterByField(selected, field);
            result.retainAll(fieldMatches);
        }

        return new ArrayList<>(result);
    }

    public static String filterFromJson(String body) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Filter filter = mapper.readValue(body, Filter.class);
        List<Activity> filtered = filterActivities(filter);
        return (getUpcomingActivities(filtered));
    }
}