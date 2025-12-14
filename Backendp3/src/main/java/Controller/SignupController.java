package Controller;

import Classes.Activity;
import Classes.Participant;
import Database.DataLoader;
import Events.FullyBooked;
import Events.Notified;
import Events.SignedUp;
import Events.Verified;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.SignUpRequest;

import java.util.List;

import static Events.Verified.verifyNotAlreadySignedUp;

public class SignupController {

    private final Verified verified = new Verified();
    private final FullyBooked fullyBooked = new FullyBooked();
    private final ObjectMapper mapper = new ObjectMapper();
    TLSEmailSender realSender = new DefaultTLSEmailSender();

    private Activity activity = new Activity();
    private final SignedUp signedUp = new SignedUp();


    private static final List<Activity> activities = DataLoader.loadActivities();

    public SignUpResult processSignup( String jsonInput){


        try {

            SignUpRequest request = mapper.readValue(jsonInput,SignUpRequest.class);
            Participant participant = toParticipant(request);
             Notified notified = new Notified(activity,participant,realSender);

            Activity match = activities.stream()
                    .filter(a->a.getActivityNameAndID().equalsIgnoreCase(participant.getActivity()))
                    .findFirst()
                    .orElse(null);
                if (match==null){
                    System.out.println("no such activity");
                    return SignUpResult.fail("Aktiviteten findes ikke");
                }

            boolean valid = verified.verifyParticipant(participant);
            if (!valid){
                System.out.println("verified failed");
                return SignUpResult.fail("invalid participant data");
            }
                activity.setActivityName(match.getActivityName());
                activity.setActivityID(match.getActivityID());
                activity.setActivityCapacity(match.getActivityCapacity());
                activity.setWaitingListEnabled(match.getWaitingListEnabled());
                activity.setWaitingListEnabled(match.getWaitingListEnabled());
                System.out.println(activity.getActivityID());




            boolean isOpen = fullyBooked.isActivityOpen(
                    participant.getActivity(),
                    activity.getActivityCapacity(),

                    activity.getWaitingListEnabled()
            );

            if (!verifyNotAlreadySignedUp(activity, participant)) {return SignUpResult.fail("participant already signed up");}

            if(isOpen){
                if (activity.getWaitingListEnabled()){
                    String line = participantToString(participant);
                    System.out.println("debug" + line );
                    notified.emailNotification("WaitingList");
                    signedUp.appendParticipant(line);

                    return SignUpResult.successWaitingList(participant);
                }else {
                    return SignUpResult.fail("Activity is Full");

                }
            }


            String line = participantToString(participant);

            signedUp.appendParticipant(line);
           notified.emailNotification("SignUp");
            return SignUpResult.success(participant);

        } catch (Exception e) {
            return  SignUpResult.fail("exception: "+ "invalid JSON format");
        }
    }
    private Participant toParticipant(SignUpRequest r){ //used for verifying data
        Participant p = new Participant();

        p.setFirstName(r.getFirstName());
        p.setLastName(r.getLastName());
        p.setEmail(r.getEmail());
        p.setDateOfBirth(r.getDateOfBirth());

        p.setActivity(r.getActivity());
        return p;
    }
    private String participantToString(Participant p) { // used to return it to text for writing to file
        return "{"
                + "\"firstname\":\"" + p.getFirstName() + "\","
                + "\"lastname\":\"" + p.getLastName() + "\","
                + "\"email\":\"" + p.getEmail() + "\","
                + "\"dateofbirth\":\"" + p.getDateOfBirth() + "\","
                + "\"activity\":\"" + p.getActivity() + "\""
                + "}";
    }


}
