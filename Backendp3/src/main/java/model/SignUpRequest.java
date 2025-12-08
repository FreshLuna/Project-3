package model;

public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;

    private boolean tosAccept;
    private boolean infoSendAccept;

    private String activity;

    public SignUpRequest() {
        // Jackson needs a no-arg constructor
    }

    // --- Getters & Setters ---

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTosAccept() {
        return tosAccept;
    }

    public void setTosAccept(boolean tosAccept) {
        this.tosAccept = tosAccept;
    }

    public boolean isInfoSendAccept() {
        return infoSendAccept;
    }

    public void setInfoSendAccept(boolean infoSendAccept) {
        this.infoSendAccept = infoSendAccept;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
