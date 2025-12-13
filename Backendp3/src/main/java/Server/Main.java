package Server;

import Classes.Activity;
import Database.ActivityService;
import Events.Expired;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Expired manager = new Expired();
        manager.removeExpiredActivities();
        new ActivityService();
        List<Activity> activities = ActivityService.getAllActivities();
        System.out.println(activities);

        try {
            PostGetServer.serverStarter();
        } catch (Exception e) {
            System.err.println("Failed to start HTTPS server");
            e.printStackTrace();
        }

    }
}
