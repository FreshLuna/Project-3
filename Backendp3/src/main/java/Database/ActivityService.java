package Database;

import Classes.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityService {
    private final List<Activity> activities;
    private final Map<String, List<Activity>> tagIndex = new HashMap<>();

    // No filepath needed now
    public ActivityService() {
        this.activities = DataLoader.loadActivities(); // uses classpath
        buildTagIndex();
    }

    private void buildTagIndex() {
        for (Activity activity : activities) {
            for (String tag : activity.getTags()) {
                tagIndex.computeIfAbsent(tag, k -> new ArrayList<>()).add(activity);
            }
        }
    }

    // filtering method
    public List<Activity> filterByTags(List<String> selectedTags) {
        if (selectedTags == null || selectedTags.isEmpty()) {
            return activities;
        }

        List<Activity> result = new ArrayList<>(tagIndex.getOrDefault(
                selectedTags.get(0), Collections.emptyList()
        ));

        for (int i = 1; i < selectedTags.size(); i++) {
            String tag = selectedTags.get(i);
            List<Activity> taggedActivities = tagIndex.getOrDefault(tag, Collections.emptyList());
            result.retainAll(taggedActivities);
        }

        return result;
    }
}
