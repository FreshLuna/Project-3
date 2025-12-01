package Classes;

public class Activity {
    private int ActivityID; // ID of the activity

    private String ActivityName; // The name displayed for the activity
    private String ActivityOrganizer; // The organization who is in charge of hosting the activity

    private String TypeOfActivity; // The type of activity in question (Rowing, Climbing, etc)
    private String Instructors; // Who will be in charge of the activity during its duration

    private long DateAndTime; // yyyy/mm/dd/hh/mm/ The date and time the activity will take place
    private String Location; // The location where the activity will be held

    private String GenderGroup; // Who is the targeted gender group for this activity?
    private String AgeGroup; // Who is the targeted age group for this activity?

    private int ActivityCapacity; // The maximum capacity of participants for this activity

    private String ActivityDescription; // A description that goes over what the activity is about
    private String ActivityDifficulty; // The difficulty level of the activity (Beginner, Intermediate,

    private boolean WaitingListEnabled; // Boolean for deciding whether or not a waiting list

    public Activity() {} // for jackson

    public int getActivityID() {
        return ActivityID;
    }

    public void setActivityID(int activityID) {
        ActivityID = activityID;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityOrganizer() {
        return ActivityOrganizer;
    }

    public void setActivityOrganizer(String activityOrganizer) {
        ActivityOrganizer = activityOrganizer;
    }

    public String getTypeOfActivity() {
        return TypeOfActivity;
    }

    public void setTypeOfActivity(String typeOfActivity) {
        TypeOfActivity = typeOfActivity;
    }

    public String getInstructors() {
        return Instructors;
    }

    public void setInstructors(String instructors) {
        Instructors = instructors;
    }

    public long getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getGenderGroup() {
        return GenderGroup;
    }

    public void setGenderGroup(String genderGroup) {
        GenderGroup = genderGroup;
    }

    public String getAgeGroup() {
        return AgeGroup;
    }

    public void setAgeGroup(String ageGroup) {
        AgeGroup = ageGroup;
    }

    public int getActivityCapacity() {
        return ActivityCapacity;
    }

    public void setActivityCapacity(int activityCapacity) {
        ActivityCapacity = activityCapacity;
    }

    public String getActivityDescription() {
        return ActivityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        ActivityDescription = activityDescription;
    }

    public String getActivityDifficulty() {
        return ActivityDifficulty;
    }

    public void setActivityDifficulty(String activityDifficulty) {
        ActivityDifficulty = activityDifficulty;
    }

    public boolean isWaitingListEnabled() {
        return WaitingListEnabled;
    }

    public void setWaitingListEnabled(boolean waitingListEnabled) {
        WaitingListEnabled = waitingListEnabled;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "ActivityID=" + ActivityID +
                ", ActivityName='" + ActivityName + '\'' +
                ", ActivityOrganizer='" + ActivityOrganizer + '\'' +
                ", TypeOfActivity='" + TypeOfActivity + '\'' +
                ", Instructors='" + Instructors + '\'' +
                ", DateAndTime=" + DateAndTime +
                ", Location='" + Location + '\'' +
                ", GenderGroup='" + GenderGroup + '\'' +
                ", AgeGroup='" + AgeGroup + '\'' +
                ", ActivityCapacity=" + ActivityCapacity +
                ", ActivityDescription='" + ActivityDescription + '\'' +
                ", ActivityDifficulty='" + ActivityDifficulty + '\'' +
                ", WaitingListEnabled=" + WaitingListEnabled +
                '}';
    }
}