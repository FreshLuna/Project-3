package Events;
import Classes.Activity;
import Classes.Participant;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import static Events.Expired.hasExpired;


public class Verified {

    //PUBLIC METHODS
    public boolean verifyParticipant(Participant p) {
        //Clean first
        p.setFirstName(cleanName(p.getFirstName()));
        p.setLastName(cleanName(p.getLastName()));
        p.setEmail(cleanEmail(p.getEmail()));
        p.setDateOfBirth(p.getDateOfBirth() != null ? p.getDateOfBirth().trim() : null);

        //Validate cleaned fields
        boolean valid = true;
        if (isMissing(p.getFirstName()) || !isAlpha(p.getFirstName())) valid = false;
        if (isMissing(p.getLastName()) || !isAlpha(p.getLastName())) valid = false;
        if (!isValidEmail(p.getEmail())) valid = false;
        if (!isValidDateOfBirth(p.getDateOfBirth())) valid = false;

        return valid;
    }

    public static boolean verifyNotAlreadySignedUp(Activity activity, Participant participant) {

        // Clean participant fields first
        String cleanedFirst = cleanName(participant.getFirstName());
        String cleanedEmail = cleanEmail(participant.getEmail());

        if (cleanedFirst == null || cleanedEmail == null) {
            System.out.println("Invalid participant data.");
            return false;
        }

        // Correct file path
        String activityName = activity.getActivityNameAndID();
        Path filePath = Paths.get("src/main/sources/events/" + activityName + "_users.txt");
        System.out.println(filePath);

        if (!Files.exists(filePath)) {
            System.out.println("Could not read user file: ");

            return false;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                // Extract fields manually from JSON-like string
                String existingFirst = extractJsonValue(line, "firstname");
                String existingEmail = extractJsonValue(line, "email");

                // Clean loaded values
                existingFirst = cleanName(existingFirst);
                existingEmail = cleanEmail(existingEmail);

                if (existingFirst == null || existingEmail == null)
                    continue;

                boolean firstMatch = cleanedFirst.equalsIgnoreCase(existingFirst);
                boolean emailMatch = cleanedEmail.equalsIgnoreCase(existingEmail);

                if (firstMatch && emailMatch) {
                    System.out.println("User is already signed up to the activity.");
                    return false;
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read user file: " + filePath);
            return false;
        }

        return true; // user not found → safe to sign up
    }

    public boolean verifyActivity(Activity a) {
        //Clean first
        a.setActivityName(cleanActivityName(a.getActivityName()));
        a.setLocation(removeExtraSpaces(a.getLocation()));
        a.setAgeGroup(cleanAgeGroup(a.getAgeGroup()));

        //Validate cleaned fields
        if (isMissing(a.getActivityName())) return(false);
        if (a.getActivityCapacity() <= 0) return(false);
        if (isMissing(a.getLocation())) return(false);
        if (!isValidAgeGroup(a.getAgeGroup())) return(false);
        if (hasExpired(a)) return(false);

        return true;
    }

    //STRING CLEANING HELPERS
    //Check if a string is null or empty after trimming
    private boolean isMissing(String s) {
        return s == null || s.trim().isEmpty();
    }

    //Remove numbers and special characters, trim, and capitalize first letter
    private static String cleanName(String input) {
        if (input == null) return null;
        input = input.trim().replaceAll("[^a-zA-Z]", ""); // remove numbers/special chars
        if (input.isEmpty()) return null;
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }

    //Check if string contains only letters A-Z or a-z
    private boolean isAlpha(String input) {
        return input != null && input.trim().matches("[a-zA-Z]+");
    }

    //Lowercase the email and trim whitespace
    private static String cleanEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }

    //Remove leading/trailing spaces and collapse multiple spaces inside
    private String removeExtraSpaces(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("\\s+", " ");
    }

    //Make activity name lowercase, remove unsafe characters, and replace spaces with hyphens
    private String cleanActivityName(String name) {
        if (name == null) return null;
        name = name.trim().toLowerCase();
        name = name.replaceAll("[^a-z0-9\\- ]", "");
        name = name.replaceAll("\\s+", "-");
        return name;
    }

    //Validate email using a simple regex
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.trim().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");
    }

    //Check if date of birth is in the correct format dd/MM/yyyy
    private boolean isValidDateOfBirth(String dob) {
        if (dob == null) return false;
        return dob.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    //Age group must be digits followed by + (e.g., "25+")
    private boolean isValidAgeGroup(String age) {
        if (age == null) return false;
        return age.matches("\\d+\\+");
    }

    //Remove all characters except digits and + from age group
    private String cleanAgeGroup(String age) {
        if (age == null) return null;
        return age.replaceAll("[^0-9+]", "");
    }

    //Simple JSON extraction
    private static String extractJsonValue(String json, String key) {
        // Example pattern: "firstname":"pizza"
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return null;

        start += search.length();
        int end = json.indexOf("\"", start);

        if (end == -1) return null;

        return json.substring(start, end);
    }

    //TEST METHOD FOR VERIFYING WHETHER PARTICIPANT IS ALREADY SIGNED UP
   /* public static void main(String[] args) {

        Verified verifier = new Verified();

        // --- Create a test activity ---
        Activity activity = new Activity();
        activity.setActivityName("diller");   // MUST match your filename: diller_users.txt

        // --- Create a test participant ---
        Participant p = new Participant();
        p.setFirstName("Pizz");
        p.setLastName("Man");
        p.setEmail("pizza@man.dk");
        p.setDateOfBirth("01/01/2000");

        // --- Run duplicate check ---
        String check = verifier.verifyNotAlreadySignedUp(activity, p);

        System.out.println("----- TEST RESULT -----");
        System.out.println("Activity name: " + activity.getActivityName());
        System.out.println("Participant: " + p.getFirstName() + " " + p.getLastName());
        System.out.println("Email: " + p.getEmail());
        System.out.println("------------------------");
        System.out.println("Verification result: " + check);
        System.out.println("------------------------");
    }
    */
}
