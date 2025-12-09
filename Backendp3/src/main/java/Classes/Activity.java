package Classes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    // consider removing "Activity" prefix
    // consider using camelCase

    public int ActivityID; // ID of the activity
    @JsonProperty("ActivityName")
    private String ActivityName; // The name displayed for the activity
    private String ActivityOrganizer; // The organization who is in charge of hosting the activity

    private String TypeOfActivity; // The type of activity in question (Rowing, Climbing, etc)
    private String Instructors; // Who will be in charge of the activity during its duration

    private long DateAndTime; // yyyy/mm/dd/hh/mm/ The date and time the activity will take place
    private String Location; // The location where the activity will be held

    private String GenderGroup; // Who is the targeted gender group for this activity?
    private String AgeGroup; // Who is the targeted age group for this activity?
    @JsonProperty("ActivityCapacity")
    private int ActivityCapacity; // The maximum capacity of participants for this activity
    private int WaitingListCapacity; // The maximum capacity of the waiting list (if enabled)

    private String ActivityDescription; // A description that goes over what the activity is about
    private String ActivityDifficulty; // The difficulty level of the activity (Beginner, Intermediate,

    public boolean WaitingListEnabled; // Boolean for deciding whether to have a waiting list
    public List<String> Tags;

    private String ImgUrl;

    public Activity() {}

    // Getters and setters
    public int getActivityID() { return activityID; }
    public void setActivityID(int activityID) { this.activityID = activityID; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getActivityOrganizer() { return activityOrganizer; }
    public void setActivityOrganizer(String activityOrganizer) { this.activityOrganizer = activityOrganizer; }

    public String getTypeOfActivity() { return typeOfActivity; }
    public void setTypeOfActivity(String typeOfActivity) { this.typeOfActivity = typeOfActivity; }

    public String getInstructors() { return instructors; }
    public void setInstructors(String instructors) { this.instructors = instructors; }

    public long getDateAndTime() { return dateAndTime; }
    public void setDateAndTime(long dateAndTime) { this.dateAndTime = dateAndTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getGenderGroup() { return genderGroup; }
    public void setGenderGroup(String genderGroup) { this.genderGroup = genderGroup; }

    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }

    public int getActivityCapacity() { return activityCapacity; }
    public void setActivityCapacity(int activityCapacity) { this.activityCapacity = activityCapacity; }

    public int getWaitingListCapacity() { return waitingListCapacity; }
    public void setWaitingListCapacity(int waitingListCapacity) { this.waitingListCapacity = waitingListCapacity; }

    public String getActivityDescription() { return activityDescription; }
    public void setActivityDescription(String activityDescription) { this.activityDescription = activityDescription; }

    public String getActivityDifficulty() { return activityDifficulty; }
    public void setActivityDifficulty(String activityDifficulty) { this.activityDifficulty = activityDifficulty; }

    public boolean getWaitingListEnabled() { return waitingListEnabled; }
    public void setWaitingListEnabled(boolean waitingListEnabled) { this.waitingListEnabled = waitingListEnabled; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
}
