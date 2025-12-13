package Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
    private long dateAndTime; // stored as yyyyMMddmm

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

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "Activity{id=" + activityID + "}";
        }
    }


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

    @JsonIgnore
    public String getWeekdays() {
        String s = String.format("%012d", dateAndTime);
        String datePart = s.substring(0, 8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(datePart, formatter);
        // Get the day of the week
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // MONDAY=1 ... SUNDAY=7
        String[] weekdays = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
        return weekdays[dayOfWeek.getValue() - 1]; // subtract 1 because array is 0-indexed


    }
    @JsonIgnore
    public String getActivityNameAndID() {
        System.out.println(activityName+activityID);
        return activityName+activityID; }

}
