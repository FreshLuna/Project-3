package Controller;

import Classes.Activity;
import Classes.Participant;
import Events.FullyBooked;
import Events.Notified;
import Events.Verified;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.SignUpRequest;

import static Events.Verified.verifyNotAlreadySignedUp;

public class SignupController {

    private  Verified verified = new Verified();
    private  FullyBooked fullyBooked = new FullyBooked();
    private final ObjectMapper mapper = new ObjectMapper();
    private TLSEmailSender realSender = new DefaultTLSEmailSender();
    private ActivityProvider activityProvider = new FileActivityProvider();
    private ParticipantRepository participantRepository = new FileParticipantProvider();


    public SignupController() {
    }

    public SignupController(TLSEmailSender realSender, ActivityProvider activityProvider, ParticipantRepository participantRepository, FullyBooked fullyBooked, Verified verified) {
        this.realSender = realSender;
        this.activityProvider = activityProvider;
        this.participantRepository = participantRepository;
        this.fullyBooked= fullyBooked;
        this.verified = verified;
    }

    public SignUpResult processSignup( String jsonInput){


        try {

            SignUpRequest request = mapper.readValue(jsonInput,SignUpRequest.class);
            Participant participant = toParticipant(request);
            Activity activity = activityProvider.getActivity(participant.getActivity());
            if(activity==null){
                return SignUpResult.fail("Aktiviteten findes ikke");
            }


            boolean valid = verified.verifyParticipant(participant);
            if (!valid){

                return SignUpResult.fail("invalid participant data");
            }




            boolean isOpen = fullyBooked.isActivityOpen(
                    participant.getActivity(),
                    activity.getActivityCapacity(),
                    activity.getWaitingListEnabled()
            );

            if (!verifyNotAlreadySignedUp(activity, participant)) {
                return SignUpResult.fail("participant already signed up");}

            Notified notified = new Notified(activity,participant,realSender);


            if(isOpen){
                if (activity.getWaitingListEnabled()){
                    participantRepository.addParticipant(participant);

                    notified.emailNotification("WaitingList");


                    return SignUpResult.successWaitingList(participant);
                }else {
                    return SignUpResult.fail("Activity is Full");

                }
            }


            participantRepository.addParticipant(participant);

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



}
