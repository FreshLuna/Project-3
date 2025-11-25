package Classes;

public class Activity {
    int ActivityID; // ID of the activity

    String ActivityName; // The name displayed for the activity
    String ActivityOrganizer; // The organization who is in charge of hosting the activity

    String TypeOfActivity; // The type of activity in question (Rowing, Climbing, etc)
    String Instructors; // Who will be in charge of the activity during its duration

    String DateAndTime; // The date and time the activity will take place
    String Address; // The location where the activity will be held

    String GenderGroup; // Who is the targeted gender group for this activity?
    String AgeGroup; // Who is the targeted age group for this activity?

    int ActivityCapacity; // The maximum capacity of participants for this activity

    String ActivityDescription; // A description that goes over what the activity is about
    String ActivityDifficulty; // The difficulty level of the activity (Beginner, Intermediate,

    boolean WaitingListEnabled; // Boolean for deciding whether or not a waiting list

    int WaitingListCapacity; // The maximum capacity for a given waiting list
}
