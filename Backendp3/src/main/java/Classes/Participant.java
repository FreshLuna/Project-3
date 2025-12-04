package Classes;

public class Participant {
    private int UserID; // ID of the participant

    private String FirstName; // First name
    private String LastName; // Last name
    private String DateOfBirth; // Date of birth
    private String Email; // Email address of the participant

    private Boolean AalborgTryOutIsAllowedToSendMessage; // This determines whether or not we are allowed to send them a notification message

    public Participant() {}

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getAalborgTryOutIsAllowedToSendMessage() {
        return AalborgTryOutIsAllowedToSendMessage;
    }

    public void setAalborgTryOutIsAllowedToSendMessage(Boolean aalborgTryOutIsAllowedToSendMessage) {
        AalborgTryOutIsAllowedToSendMessage = aalborgTryOutIsAllowedToSendMessage;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "UserID=" + UserID +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", Email='" + Email + '\'' +
                ", AalborgTryOutIsAllowedToSendMessage=" + AalborgTryOutIsAllowedToSendMessage +
                '}';
    }
}