package Events;

import Classes.Activity;
import Classes.Participant;

public class Notification {
private String message;

private Participant participant;
private Activity activity;

    public Notification(Activity activity, Participant participant) {
        this.activity = activity;
        this.participant = participant;
    }

    public String emailNotification(String message){
   return switch(message){
       case "SignUp" -> {

           mail(signUpNotification());
           yield signUpNotification();
       }
       case "Canceled" -> {
           mail(cancelNotification());
           yield cancelNotification();
       }
       case "WaitingList" ->{
            mail(waitingListNotification());
          yield waitingListNotification();
       }
       default -> throw new IllegalStateException("Unexpected value: " + message);
   };
    }

    private String signUpNotification() {
        return String.format("""
                Hej %s %s,
                
                Vi bekræfter hermed din tilmelding til følgende aktivitet:
                
                Aktivitet: %s
                
                """,
                participant.getFirstName(),
                participant.getLastName(),
                activity.getActivityName()

                );
    }

    private String cancelNotification() {
        return String.format("""
                Hej %s %s,
                
                Vi bekræfter hermed din afmelding fra "%s".
                """,
                participant.getFirstName(),
                participant.getLastName(),
                activity.getActivityName()
        );
    }

    private String waitingListNotification(){
        return String.format("""
                Hej %s %s,
                
                Aktiviteten %s er fuldt booket, og du er nu tilmeldt ventelisten.
                Hvis der bliver en ledig plads, vil du automatisk blive tilmeldt.
                """,
                participant.getFirstName(),
                participant.getLastName(),
                activity.getActivityName()
        );
    }
    public void mail(String msg){

        /* here should the call to and email server go
         We need maven dependencies  and a smpt wait to see Luna code
        *  */

        System.out.println("send email to at: "+participant.getEmail() +"\n"+ msg);

    }
}