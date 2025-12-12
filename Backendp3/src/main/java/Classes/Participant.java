package Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {
    private int UserID; // ID of the participant
    @JsonProperty("firstname")
    private String FirstName; // First name
    @JsonProperty("lastname")
    private String LastName; // Last name

    public Participant(String firstName, String lastName, String email, String activity) {// try to use this for removing and notifying
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.activity = activity;
    }

    // @JsonIgnore
    @JsonProperty("dateofbirth")
    private String DateOfBirth; // Date of birth
    @JsonProperty("email")
    private String Email; // Email address of the participant

    private Boolean AalborgTryOutIsAllowedToSendMessage; // This determines whether or not we are allowed to send them a notification message

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    private String activity;
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