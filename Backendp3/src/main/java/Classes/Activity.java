package Classes;

public class Activity {
    public int ActivityID; // ID of the activity

    public String ActivityName; // The name displayed for the activity
    public String ActivityOrganizer; // The organization who is in charge of hosting the activity

    public String TypeOfActivity; // The type of activity in question (Rowing, Climbing, etc)
    public String Instructors; // Who will be in charge of the activity during its duration

    public long DateAndTime; // yyyy/mm/dd/hh/mm/ The date and time the activity will take place
    public String Location; // The location where the activity will be held

    public String GenderGroup; // Who is the targeted gender group for this activity?
    public String AgeGroup; // Who is the targeted age group for this activity?

    public int ActivityCapacity; // The maximum capacity of participants for this activity

    public String ActivityDescription; // A description that goes over what the activity is about
    public String ActivityDifficulty; // The difficulty level of the activity (Beginner, Intermediate,

    public boolean WaitingListEnabled; // Boolean for deciding whether or not a waiting list

    public int WaitingListCapacity; // The maximum capacity for a given waiting list

    public Activity() {} // for jackson

}
