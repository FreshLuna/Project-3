package controller;

import Classes.Activity;
import Classes.Participant;
import Database.DataLoader;
import Events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CancelRequest;
import Events.Canceled;


public class CancelController {
    private final ObjectMapper mapper = new ObjectMapper();
    Canceled canceled = new Canceled();

    //recieves Firstname lastname email and activity as a json list
    public CancelResult processCancel(String JsonInput){
        try {
            CancelRequest cancelRequest = mapper.readValue(JsonInput,CancelRequest.class);
            Participant participant = toParticipant(cancelRequest);
            Activity activity = toActivity(cancelRequest);
            boolean success = canceled.removeParticipantByDetails(
                    activity.getActivityName(), 
                    participant.getFirstName(), 
                    participant.getLastName(), 
                    participant.getEmail()
            );
            
            if (success) {
                return new CancelResult(true, "Deltager fjernet", participant);
            } else {
                return new CancelResult(false, "Deltager kunne ikke findes", null);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Participant toParticipant(CancelRequest r){
        Participant p = new Participant();
        p.setFirstName(r.getFirstName());
        p.setLastName(r.getLastName());
        p.setEmail(r.getEmail());
        p.setActivity(r.getActivity());
        return p;
    }

    private Activity toActivity(CancelRequest r){
        Activity a = new Activity();

        a.setActivityName(r.getActivity());

        return a;
    }


}
