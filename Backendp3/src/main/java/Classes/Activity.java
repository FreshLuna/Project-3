package Classes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    // consider removing "Activity" prefix
    // consider using camelCase

    @JsonProperty("ActivityID")
    private int activityID;

    @JsonProperty("ActivityName")
    private String activityName;

    @JsonProperty("ActivityOrganizer")
    private String activityOrganizer;

    @JsonProperty("TypeOfActivity")
    private String typeOfActivity;

    @JsonProperty("Instructors")
    private String instructors;

    @JsonProperty("DateAndTime")
    private long dateAndTime;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("GenderGroup")
    private String genderGroup;

    @JsonProperty("AgeGroup")
    private String ageGroup;

    @JsonProperty("ActivityCapacity")
    private int activityCapacity;

    @JsonProperty("WaitingListCapacity")
    private int waitingListCapacity;

    @JsonProperty("ActivityDescription")
    private String activityDescription;

    @JsonProperty("ActivityDifficulty")
    private String activityDifficulty;

    @JsonProperty("WaitingListEnabled")
    private boolean waitingListEnabled;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("ImgUrl")
    private String imgUrl;

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
