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

    public CancelResult processCancel(String JsonInput){
        try {
            CancelRequest cancelRequest = mapper.readValue(JsonInput,CancelRequest.class);
            Participant participant = toParticipant(cancelRequest);
            Activity activity = toActivity(cancelRequest);
            canceled.checkParticipant(activity,participant);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Participant toParticipant(CancelRequest r){ //used in check participant
        Participant p = new Participant();
        p.setUserID(r.getUserID());
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
