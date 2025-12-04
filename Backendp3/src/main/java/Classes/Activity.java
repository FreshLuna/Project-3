package Classes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// we tell jackson to ignore unknown fields
// otherwise it starts bean deserializing or something
// actually: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "WaitingListCapacity" (class Classes.Activity), not marked as ignorable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    // consider removing "Activity" prefix
    // consider using camelCase

    public int ActivityID; // ID of the activity

    private String ActivityName; // The name displayed for the activity
    private String ActivityOrganizer; // The organization who is in charge of hosting the activity

    private String TypeOfActivity; // The type of activity in question (Rowing, Climbing, etc)
    private String Instructors; // Who will be in charge of the activity during its duration

    private long DateAndTime; // yyyy/mm/dd/hh/mm/ The date and time the activity will take place
    private String Location; // The location where the activity will be held

    private String GenderGroup; // Who is the targeted gender group for this activity?
    private String AgeGroup; // Who is the targeted age group for this activity?

    public int ActivityCapacity; // The maximum capacity of participants for this activity
    private int WaitingListCapacity; // The maximum capacity of the waiting list (if enabled)
    private int ActivityCapacity; // The maximum capacity of participants for this activity

    private String ActivityDescription; // A description that goes over what the activity is about
    private String ActivityDifficulty; // The difficulty level of the activity (Beginner, Intermediate,

    public boolean WaitingListEnabled; // Boolean for deciding whether to have a waiting list
    public List<String> Tags;

    public Activity() {} // for jackson

    public int getActivityID() {
        return ActivityID;
    }
    public void setActivityID(int ActivityID) {
        this.ActivityID = ActivityID;
    }

    public String getActivityName() {
        return ActivityName;
    }
    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getActivityOrganizer() {
        return ActivityOrganizer;
    }
    public void setActivityOrganizer(String ActivityOrganizer) {
        this.ActivityOrganizer = ActivityOrganizer;
    }

    public String getTypeOfActivity() {
        return TypeOfActivity;
    }
    public void setTypeOfActivity(String TypeOfActivity) {
        this.TypeOfActivity = TypeOfActivity;
    }

    public String getInstructors() {
        return Instructors;
    }
    public void setInstructors(String Instructors) {
        this.Instructors = Instructors;
    }

    public long getDateAndTime() {
        return DateAndTime;
    }
    public void setDateAndTime(long DateAndTime) {
        this.DateAndTime = DateAndTime;
    }

    public String getLocation() {
        return Location;
    }
    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getGenderGroup() {
        return GenderGroup;
    }
    public void setGenderGroup(String GenderGroup) {
        this.GenderGroup = GenderGroup;
    }

    public String getAgeGroup() {
        return AgeGroup;
    }
    public void setAgeGroup(String AgeGroup) {
        this.AgeGroup = AgeGroup;
    }

    public int getActivityCapacity() {
        return ActivityCapacity;
    }
    public void setActivityCapacity(int ActivityCapacity) {
        this.ActivityCapacity = ActivityCapacity;
    }

    public int getWaitingListCapacity() {
        return WaitingListCapacity;
    }
    public void setWaitingListCapacity(int WaitingListCapacity) {
        this.WaitingListCapacity = WaitingListCapacity;
    }

    public String getActivityDescription() {
        return ActivityDescription;
    }
    public void setActivityDescription(String ActivityDescription) {
        this.ActivityDescription = ActivityDescription;
    }

    public String getActivityDifficulty() {
        return ActivityDifficulty;
    }
    public void setActivityDifficulty(String ActivityDifficulty) {
        this.ActivityDifficulty = ActivityDifficulty;
    }

    public boolean getWaitingListEnabled() {
        return WaitingListEnabled;
    }
    public void setWaitingListEnabled(boolean WaitingListEnabled) {
        this.WaitingListEnabled = WaitingListEnabled;
    }

    public List<String> getTags() {
        return Tags;
    }
    public void setTags(List<String> Tags) {
        this.Tags = Tags;
    }
}