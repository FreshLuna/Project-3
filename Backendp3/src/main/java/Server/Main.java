package Server;

import Classes.Activity;
import Database.ActivityService;
import Events.Expired;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //REMOVES OLD ACTIVITIES
        Expired manager = new Expired();
        manager.removeExpiredActivities();

        //LOADS ACTIVITIES
        new ActivityService();
        List<Activity> activities = ActivityService.getAllActivities();

        //ATTEMPTS TO OPEN SERVER
        try {
            PostGetServer.serverStarter();
        } catch (Exception e) {
            System.err.println("Failed to start HTTPS server");
            e.printStackTrace();
        }

    }
}
