package Events;

import Classes.Activity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Database.DataLoader.loadActivities;
import static Events.Removed.removeActivityById;
import static java.lang.Long.parseLong;


public class Expired { //returns true if event is expired
    private List<Activity> activities;

    public Expired() {
        // Make a modifiable copy of activities
        this.activities = new ArrayList<>(loadActivities());
    }

    public static Boolean hasExpired(Activity inputActivity) {
        LocalDateTime now = LocalDateTime.now(); // Create a date object
        DateTimeFormatter FormattedDate = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        long currentDate = parseLong(now.format(FormattedDate));
        return inputActivity.getDateAndTime() < currentDate;
    }

    public void removeExpiredActivities() throws Exception {
        for (Activity a : activities) {
            if (Expired.hasExpired(a)) {
                System.out.println("Removing expired activity ID: " + a.getActivityID() + " (" + a.getActivityName() + ")");
                removeActivityById(a.getActivityID());
            }
        }
    }
}

