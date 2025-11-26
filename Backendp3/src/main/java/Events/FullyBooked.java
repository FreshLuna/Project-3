package Events;
import Classes.Activity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class fullyBooked {

    //If activity is fully booked, then print "Aktiviteten * er fuldt booket"
    public void checkActivity(Activity activity) {
        if (isActivityFullyBooked(activity.ActivityName, activity.ActivityCapacity, activity.WaitingListEnabled)) {
            System.out.println("Aktiviteten " + activity.ActivityName + " er fuldt booket");
        }
    }

    // Checks if an activity is fully booked if it is return isFullyBooked
    public static boolean isActivityFullyBooked(String activityName, int activityCapacity, boolean waitingListEnabled) {
        try {
            Path filePath = Paths.get("src/main/sources/events/" + activityName + "_users.txt");

            // Check if file exists
            if (!Files.exists(filePath)) {
                return false;
            }

            // Read all lines from the file to count participants
            List<String> users = Files.readAllLines(filePath);
            int participantCount = users.size();

            // Activity is fully booked if: participant count >= capacity AND waiting list is enabled
            boolean isFullyBooked = (participantCount >= activityCapacity) && waitingListEnabled;

            if (isFullyBooked) {
                System.out.println("Aktiviteten " + activityName + " er fuldt booket med " + participantCount + " deltagere");
            }

            return isFullyBooked;
        } catch (Exception e) {
            System.err.println("Fejl ved læsning af deltager fil for " + activityName + ": " + e.getMessage());
            return false;
        }
    }

}




