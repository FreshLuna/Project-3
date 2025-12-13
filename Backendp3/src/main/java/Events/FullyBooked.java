package Events;

import Classes.Activity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static Config.FilePaths.EVENTS_FOLDER;

public class FullyBooked {
    //If activity is fully booked, then print "Aktiviteten * er fuldt booket"
    public void checkActivity(Activity activity) {
        if (isActivityOpen(activity.getActivityName(), activity.getActivityCapacity(), activity.getWaitingListEnabled())) {
            System.out.println("Aktiviteten " + activity.getActivityName() + " er fuldt booket");
        } else {
                System.out.println("Aktiviteten " + activity.getActivityName() + " er ikke fuldt booket");
            }
    }

    // Checks if an activity is fully booked if it is return isFullyBooked
    public static boolean isActivityOpen(String activityName, int activityCapacity, boolean waitingListEnabled) {
        try {
            Path filePath = Paths.get(EVENTS_FOLDER + activityName + "_users.txt");

            // If no file exists, there are no users → activity not full
            if (!Files.exists(filePath)) {
                System.out.println("no file");
                return false;
            }

            // Read all lines to count participants
            List<String> users = Files.readAllLines(filePath);
            int participantCount = users.size();
            System.out.println(participantCount);

            // Activity is fully booked when participant count >= capacity
            boolean isActivityOpen = participantCount >= activityCapacity;
            System.out.println(isActivityOpen);



            if (isActivityOpen) {
                System.out.println("Aktiviteten " + activityName + " har " + participantCount + " brugere på listen i alt");

                if (waitingListEnabled) {
                    System.out.println("Venteliste er aktiveret – nye brugere kan stadig tilføjes til ventelisten.");
                } else {
                    System.out.println("Ingen venteliste – ingen nye brugere kan tilføjes.");
                }
            }

            return isActivityOpen;

        } catch (Exception e) {
            System.err.println("Fejl ved læsning af deltager fil for " + activityName + ": " + e.getMessage());
            return false;
        }
    }
}




