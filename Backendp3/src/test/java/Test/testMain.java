package Test;
import Classes.*;
import Events.Canceled;
import Events.Published;

public class testMain {

    public static void main(String[] args) {
        Published pub = new Published();
        Canceled CC =  new Canceled();
        Activity a = new Activity();
        Participant p = new Participant();


        pub.publish(a);
        p.setUserID(1337);
       // CC.checkParticipant(a,p);
        //CC.removeParticipant("Yoga", p.getUserID());


    }
}

