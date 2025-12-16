package Controller;

import Classes.Activity;

import java.util.List;

public interface ActivityProvider {

    Activity getActivity(String activityNameAndID);
    List<Activity> getAllActivities();
}

