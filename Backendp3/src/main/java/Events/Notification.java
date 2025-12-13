package Events;

import Classes.Activity;
import Classes.Participant;
import Controller.TLSEmailSender;

public class Notification {
private String message;

private Participant participant;
private Activity activity;
private TLSEmailSender emailSender;

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
       case "MovedFromWaitingList" ->{
           mail(movedFromWaitingList());
           yield movedFromWaitingList();
       }
       case "test" ->{
           mail(test());
           yield  test();
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
    private String movedFromWaitingList(){
        return  String.format("""
                Hej %s %s
                
                Der er nu en ledig plads på %s, og du er blevet automatisk tilmeldt.
                """,
                participant.getFirstName(),
                participant.getLastName(),
                activity.getActivityName()



        );



    }
    private String test(){
        return String.format("""
                    
                    Hey %s %s
                    
                    du er nu nummer  på venteliste til %s
                    """,
                participant.getFirstName(),
                participant.getLastName(),
                activity.getActivityName()
        );
    }
    public void mail(String msg){

      /*  try {
            emailSender.sendTLSMail(participant.getEmail(),msg,activity.getActivityName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            */ //need file
        System.out.println("send email to at: "+participant.getEmail() +"\n"+ msg);

    }
}