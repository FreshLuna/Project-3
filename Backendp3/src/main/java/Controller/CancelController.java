package Controller;

import Classes.Activity;
import Classes.Participant;
import Events.Notified;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.CancelRequest;

import java.util.List;

public class CancelController {

    private final ObjectMapper mapper = new ObjectMapper();
    private ParticipantRepository participantRepository = new FileParticipantProvider();
    private ActivityProvider activityProvider = new FileActivityProvider();
    private TLSEmailSender realSender = new DefaultTLSEmailSender();

    public CancelResult processCancel(String JsonInput) {
        try {
            CancelRequest cancelRequest =
                    mapper.readValue(JsonInput, CancelRequest.class);


            Participant participant = toParticipant(cancelRequest);
            Activity activity =
                    activityProvider.getActivity(cancelRequest.getActivity());

            if (activity == null) {
                return new CancelResult(false, "Aktiviteten findes ikke", null);
            }

            int participantPosition =
                    participantRepository.indexOfParticipant(
                            activity.getActivityNameAndID(),
                            participant
                    );

            boolean success =
                    participantRepository.removeParticipant(participant);

            if (!success) {
                return new CancelResult(false, "Afmelding mislykkedes", null);
            }


            boolean moveUp =
                    participantRepository.hasWaitingListPromotion(
                            activity.getActivityNameAndID(),
                            participantPosition,
                            activity.getActivityCapacity(),
                            activity.getWaitingListEnabled()
                    );

            if (moveUp) {
                Participant promoted =
                        participantRepository.getPromotedParticipant(
                                activity.getActivityNameAndID(),
                                activity.getActivityCapacity()
                        );

                if (promoted != null) {
                    Notified promotedNotification =
                            new Notified(activity, promoted, realSender);
                    promotedNotification.emailNotification("MovedFromWaitingList");
                }
            }
            List<Participant> topWaiting =
                    participantRepository.handleWaitingListAfterCancel(
                            activity.getActivityNameAndID(),
                            activity.getActivityCapacity(),
                            activity.getWaitingListEnabled(),
                            participantPosition
                    );

            int index = 1;
            for (Participant p : topWaiting) {
                Notified notify =
                        new Notified(activity, p, realSender);
                notify.upDatedTop3(index++);
            }


            Notified canceledNotification =
                    new Notified(activity, participant, realSender);
            canceledNotification.emailNotification("Canceled");

            return new CancelResult(true, "Deltager fjernet", participant);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Participant toParticipant(CancelRequest r) {
        Participant p = new Participant();
        p.setFirstName(r.getFirstName());
        p.setLastName(r.getLastName());
        p.setEmail(r.getEmail());
        p.setActivity(r.getActivity());
        return p;
    }
}
