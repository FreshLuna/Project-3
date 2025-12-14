package Server;

import Classes.Activity;
import Database.ActivityService;
import Events.Expired;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //REMOVES OLD ACTIVITIES AND LOADS ACTIVITIES
        Expired manager = new Expired();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                manager.removeExpiredActivities();
                System.out.println("15 min Routine Cleaning");
            } catch (Exception e) {
                System.err.println("Failed to remove expired activities");
                e.printStackTrace();
            }
            new ActivityService();
            try {
                System.out.println("Activities Reloading");
                //noinspection unused IntelliJ doesnt understand that we need to initialize it
                List<Activity> activities = ActivityService.getAllActivities();
            } catch (Exception e) {
                System.err.println("Failed to Load Activities");
            }
        }, 0, 5, TimeUnit.MINUTES);
        //ATTEMPTS TO OPEN SERVER
        try {
            PostGetServer.serverStarter();
        } catch (Exception e) {
            System.err.println("Failed to start HTTPS server");
            e.printStackTrace();
        }

    }
}
