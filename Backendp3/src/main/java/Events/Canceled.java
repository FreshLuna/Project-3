package Events;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static Config.FilePaths.EVENTS_FOLDER;

public class Canceled {
    public boolean removeParticipantByDetails(String activityName, String firstName, String lastName, String email) {
        Path filePath = Paths.get(EVENTS_FOLDER + activityName + "_users.txt");

        try {
            if (!Files.exists(filePath)) {
                System.out.println("File does not exist: " + filePath);
                return false;
            }

            // Read users
            List<String> users = Files.readAllLines(filePath);

            // Filter out JSON line containing matching participant details
            List<String> updatedUsers = users.stream()
                    .filter(line -> {
                        String lowerLine = line.toLowerCase();
                        return !(lowerLine.contains("\"firstname\":\"" + firstName.toLowerCase() + "\"") &&
                                lowerLine.contains("\"lastname\":\"" + lastName.toLowerCase() + "\"") &&
                                lowerLine.contains("\"email\":\"" + email.toLowerCase() + "\""));

                    })
                    .collect(Collectors.toList());

            // If nothing was removed
            if (updatedUsers.size() == users.size()) {
                System.out.println("Participant not found: " + firstName + " " + lastName + " (" + email + ")");
                return false;
            }

            // Write updated file
            Files.write(filePath, updatedUsers);
            System.out.println("Participant removed: " + firstName + " " + lastName + " from activity: " + activityName);
            return true;

        } catch (Exception e) {
            System.err.println("Fejl ved læsning af deltager fil for " + activityName + ": " + e.getMessage());
            return false;
        }
    }















}
