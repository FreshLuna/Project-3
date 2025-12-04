package Events;
import Classes.Activity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FullyBooked {
    //If activity is fully booked, then print "Aktiviteten * er fuldt booket"
    public void checkActivity(Activity activity) {
        if (isActivityOpen(activity.getActivityName(), activity.getActivityCapacity(), activity.isWaitingListEnabled())) {
            System.out.println("Aktiviteten " + activity.getActivityName() + " er fuldt booket");
        } else {
                System.out.println("Aktiviteten " + activity.getActivityName() + " er ikke fuldt booket");
            }
    }

    // Checks if an activity is fully booked if it is return isFullyBooked
    public static boolean isActivityOpen(String activityName, int activityCapacity, boolean waitingListEnabled) {
        try {
            Path filePath = Paths.get("src/main/sources/events/" + activityName + "_users.txt");

            // If no file exists, there are no users → activity not full
            if (!Files.exists(filePath)) {
                return false;
            }

            // Read all lines to count participants
            List<String> users = Files.readAllLines(filePath);
            int participantCount = users.size();

            // Activity is fully booked when participant count >= capacity
            boolean isActivityOpen = participantCount >= activityCapacity;

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




