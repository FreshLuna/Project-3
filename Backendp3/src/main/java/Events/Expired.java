package Events;
import java.time.format.DateTimeFormatter;
import Classes.Activity;

import static Database.DataLoader.loadActivities;
import static Events.Removed.removeActivityById;
import static java.lang.Long.parseLong;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    public static void main(String[] args) throws Exception {
        Expired manager = new Expired();
        manager.removeExpiredActivities();
    }
}

