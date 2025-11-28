package Events;
import Classes.Activity;
import Classes.Participant;

public class Verified {

    //PUBLIC METHODS
    public boolean verifyParticipant(Participant p) {
        //Clean first
        p.FirstName = cleanName(p.FirstName);
        p.LastName = cleanName(p.LastName);
        p.Email = cleanEmail(p.Email);
        p.DateOfBirth = p.DateOfBirth != null ? p.DateOfBirth.trim() : null;

        //Validate cleaned fields
        boolean valid = true;
        if (isMissing(p.FirstName) || !isAlpha(p.FirstName)) valid = false;
        if (isMissing(p.LastName) || !isAlpha(p.LastName)) valid = false;
        if (!isValidEmail(p.Email)) valid = false;
        if (!isValidDateOfBirth(p.DateOfBirth)) valid = false;

        return valid;
    }

    public boolean verifyActivity(Activity a) {
        //Clean first
        a.ActivityName = cleanActivityName(a.ActivityName);
        a.Location = removeExtraSpaces(a.Location);
        a.AgeGroup = cleanAgeGroup(a.AgeGroup);

        //Validate cleaned fields
        boolean valid = true;
        if (isMissing(a.ActivityName)) valid = false;
        if (a.ActivityCapacity <= 0) valid = false;
        if (isMissing(a.Location)) valid = false;
        if (!isValidAgeGroup(a.AgeGroup)) valid = false;

        return valid;
    }


    //STRING CLEANING HELPERS
    //Check if a string is null or empty after trimming
    private boolean isMissing(String s) {
        return s == null || s.trim().isEmpty();
    }

    //Remove numbers and special characters, trim, and capitalize first letter
    private String cleanName(String input) {
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
    private String cleanEmail(String email) {
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
        return dob.matches("\\d{2}/\\d{2}/\\d{4}");
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
/*
    // MAIN METHOD FOR TESTING
    public static void main(String[] args) {
        //sample Activity
        Activity activity = new Activity();
        activity.ActivityName = "  Yoga / Beginner ";
        activity.ActivityCapacity = 10;
        activity.Location = "   Main Hall  ";
        activity.ActivityOrganizer = "Alice";
        activity.TypeOfActivity = "Fitness";
        activity.Instructors = "Bob";
        activity.DateAndTime = 202511271200L; // note L suffix
        activity.GenderGroup = "All";
        activity.AgeGroup = "25+ years";
        activity.ActivityDescription = "Relaxing yoga session";
        activity.ActivityDifficulty = "Beginner";
        activity.WaitingListEnabled = true;

        //sample Participant
        Participants participant = new Participants();
        participant.FirstName = "  john123  ";
        participant.LastName = "DOE!!";
        participant.Email = " John.Doe@Example.Com ";
        participant.DateOfBirth = "01/01/2000";
        participant.UserID = 1;
        participant.AalborgTryOutIsAllowedToSendMessage = true;

        Verified verifier = new Verified();

        //Before cleaning
        System.out.println("Before cleaning:");
        System.out.println("Activity Name: " + activity.ActivityName);
        System.out.println("Activity Location: " + activity.Location);
        System.out.println("Activity Age Group: " + activity.AgeGroup);
        System.out.println("Participant FirstName: " + participant.FirstName);
        System.out.println("Participant LastName: " + participant.LastName);
        System.out.println("Participant Email: " + participant.Email);
        System.out.println("Participant Date of Birth: " + participant.DateOfBirth);

        //Run verification
        boolean activityValid = verifier.verifyActivity(activity);
        boolean participantValid = verifier.verifyParticipant(participant);

        //After cleaning
        System.out.println("\nAfter cleaning:");
        System.out.println("Activity valid: " + activityValid);
        System.out.println("Activity Name: " + activity.ActivityName);
        System.out.println("Activity Location: " + activity.Location);
        System.out.println("Activity Age Group: " + activity.AgeGroup);
        System.out.println("Participant valid: " + participantValid);
        System.out.println("Participant FirstName: " + participant.FirstName);
        System.out.println("Participant LastName: " + participant.LastName);
        System.out.println("Participant Email: " + participant.Email);
        System.out.println("Participant Date of Birth: " + participant.DateOfBirth);
    }
 */
}
