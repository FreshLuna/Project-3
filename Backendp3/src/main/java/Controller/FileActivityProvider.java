package Controller;

import Classes.Activity;
import Database.DataLoader;

import java.util.List;

public class FileActivityProvider implements ActivityProvider{
    private final List<Activity> activities = DataLoader.loadActivities();

    @Override
    public Activity getActivity(String activityNameAndID) {
        return activities.stream()
                .filter(a -> a.getActivityNameAndID().equalsIgnoreCase(activityNameAndID))
                .findFirst()
                .orElse(null);
    }


}
