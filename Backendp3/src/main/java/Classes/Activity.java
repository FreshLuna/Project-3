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
    public int getActivityID() { return ActivityID; }
    public void setActivityID(int activityID) { this.ActivityID = activityID; }

    public String getActivityName() { return ActivityName; }
    public void setActivityName(String activityName) { this.ActivityName = activityName; }

    public String getActivityOrganizer() { return ActivityOrganizer; }
    public void setActivityOrganizer(String activityOrganizer) { this.ActivityOrganizer = activityOrganizer; }

    public String getTypeOfActivity() { return TypeOfActivity; }
    public void setTypeOfActivity(String typeOfActivity) { this.TypeOfActivity = typeOfActivity; }

    public String getInstructors() { return Instructors; }
    public void setInstructors(String instructors) { this.Instructors = instructors; }

    public long getDateAndTime() { return DateAndTime; }
    public void setDateAndTime(long dateAndTime) { this.DateAndTime = dateAndTime; }

    public String getLocation() { return Location; }
    public void setLocation(String location) { this.Location = location; }

    public String getGenderGroup() { return GenderGroup; }
    public void setGenderGroup(String genderGroup) { this.GenderGroup = genderGroup; }

    public String getAgeGroup() { return AgeGroup; }
    public void setAgeGroup(String ageGroup) { this.AgeGroup = ageGroup; }

    public int getActivityCapacity() { return ActivityCapacity; }
    public void setActivityCapacity(int activityCapacity) { this.ActivityCapacity = activityCapacity; }

    public int getWaitingListCapacity() { return WaitingListCapacity; }
    public void setWaitingListCapacity(int waitingListCapacity) { this.WaitingListCapacity = waitingListCapacity; }

    public String getActivityDescription() { return ActivityDescription; }
    public void setActivityDescription(String activityDescription) { this.ActivityDescription = activityDescription; }

    public String getActivityDifficulty() { return ActivityDifficulty; }
    public void setActivityDifficulty(String activityDifficulty) { this.ActivityDifficulty = activityDifficulty; }

    public boolean getWaitingListEnabled() { return WaitingListEnabled; }
    public void setWaitingListEnabled(boolean waitingListEnabled) { this.WaitingListEnabled = waitingListEnabled; }

    public List<String> getTags() { return Tags; }
    public void setTags(List<String> tags) { this.Tags = tags; }

    public String getImgUrl() { return ImgUrl; }
    public void setImgUrl(String imgUrl) { this.ImgUrl = imgUrl; }
}
