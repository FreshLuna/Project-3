package Events;
import Classes.Participant;
import Classes.Activity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantCancelled {
    public void checkParticipant(Activity activity, Participant participant) {
        if (removeParticipant(activity.getActivityName(), participant.getUserID())) {
            System.out.println("Participant " + participant.getUserID() + " is removed from activity: " + activity.getActivityName());
        } else {
            System.out.println("Participant " + participant.getUserID() + " was NOT found in: " + activity.getActivityName());
        }
    }

    public boolean removeParticipant(String activityName, int userID) {
        Path filePath = Paths.get("src/main/sources/events/" + activityName + "_users.txt");

        try {
            if (!Files.exists(filePath)) {
                System.out.println("File does not exist: " + filePath);
                return false;
            }

            // Read users
            List<String> users = Files.readAllLines(filePath);

            // String to search for inside JSON
            String idKey = "\"userID\":" + userID;

            // Filter out JSON line containing the ID
            List<String> updatedUsers = users.stream()
                    .filter(line -> !line.contains(idKey))
                    .collect(Collectors.toList());

            // If nothing was removed
            if (updatedUsers.size() == users.size()) {
                return false;
            }

            // Write updated file
            Files.write(filePath, updatedUsers);
            return true;

        } catch (Exception e) {
            System.err.println("Fejl ved læsning af deltager fil for " + activityName + ": " + e.getMessage());
            return false;
        }
    }

}
