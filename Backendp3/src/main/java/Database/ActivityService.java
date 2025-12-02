package Database;

import Classes.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.*;

// this loads the JSON file once
// then stores a list of activities
// then creates a map <tag, List<Activity>>

public class ActivityService {
    private final List<Activity> activities; // = new ArrayList<>();
    private final Map<String, List<Activity>> tagIndex = new HashMap<>();

    public ActivityService(String filepath) {
        this.activities = DataLoader.loadActivities(filepath);
        buildTagIndex();
    }

    private void buildTagIndex() {
        for (Activity activity : activities) {
            for (String tag : activity.getTags()) {
                tagIndex.computeIfAbsent(tag, k -> new ArrayList<>()).add(activity);
            }
        }
    }

    // filtering method ---- consider moving to separate class for readability
    public List<Activity> filterByTags(List<String> selectedTags) {
        if (selectedTags == null || selectedTags.isEmpty()) {
            return activities;
        }

        // starting with first tag's list
        List<Activity> result = new ArrayList<>(tagIndex.getOrDefault(
                selectedTags.getFirst(), Collections.emptyList()
        ));

        // intersect with the rest
        for (int i = 1; i < selectedTags.size(); i++) {
            String tag = selectedTags.get(i);
            List<Activity> taggedActivities = tagIndex.getOrDefault(tag, Collections.emptyList());

            result.retainAll(taggedActivities);
        }

        return result;
    }
}
