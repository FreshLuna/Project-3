package Controller;

import Classes.Activity;
import Classes.Participant;
import Database.DataLoader;
import Events.Canceled;
import Events.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.CancelRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static Config.FilePaths.EVENTS_FOLDER;


public class CancelController {
    private final ObjectMapper mapper = new ObjectMapper();
    Canceled canceled = new Canceled();
    private static final List<Activity> activities = DataLoader.loadActivities();
    private int placeOnList;
    TLSEmailSender realSender = new DefaultTLSEmailSender();

    public CancelResult processCancel(String JsonInput){
        try {
            CancelRequest cancelRequest = mapper.readValue(JsonInput,CancelRequest.class);
            Participant participant = toParticipant(cancelRequest);
            Activity activity = toActivity(cancelRequest);
            placeOnList = IntStream.range(0, participantList(activity.getActivityName()).size())
                    .filter(i -> participantList(activity.getActivityName()).get(i).getFirstName().equals(participant.getFirstName())
                            && participantList(activity.getActivityName()).get(i).getLastName().equals(participant.getLastName())
                            && participantList(activity.getActivityName()).get(i).getEmail().equals(participant.getEmail()))
                    .findFirst()
                    .orElse(-1);

            System.out.println(placeOnList);
            boolean success = canceled.removeParticipantByDetails(
                    activity.getActivityName(),
                    participant.getFirstName(),
                    participant.getLastName(),
                    participant.getEmail()
            );

            if (success) {


                if (checkForWaiting(activity)){
                    Participant p = participantList(activity.getActivityName()).get(activity.getActivityCapacity()-1);

                    Notification notificationToPart = new Notification(activity, p ,realSender);
                    System.out.println("here we see if a new participant get the message"+p.getLastName());
                    notificationToPart.emailNotification("MovedFromWaitingList");
                }
                Notification notification = new Notification(activity,participant,realSender);

                notification.emailNotification("Canceled");
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
            try{
        Activity match = activities.stream()
                .filter(act->act.getActivityName().equalsIgnoreCase(r.getActivity()))
                .findFirst()
                .orElse(null);


                a.setActivityName(r.getActivity());
                assert match != null;
                a.setActivityCapacity(match.getActivityCapacity());
                a.setWaitingListEnabled(match.getWaitingListEnabled());


        }catch (Exception e){
                e.printStackTrace();
            }
        return a;
    }
    private boolean checkForWaiting (Activity act){

           List<Participant> participants = participantList(act.getActivityName());

            int participantCount = participants.size();
            boolean hasWaitingList = participantCount >= act.getActivityCapacity();
            boolean participantNotOnWaitingList = placeOnList<act.getActivityCapacity();
        System.out.println(participantNotOnWaitingList+"where is ");
        return act.getWaitingListEnabled() && hasWaitingList && participantNotOnWaitingList;
    }

    private List<Participant> participantList(String activityName){
        Path filePath = Paths.get(EVENTS_FOLDER + activityName + "_users.txt");
        ObjectMapper participantMapper = new ObjectMapper();
        try {
        List<String> lines = Files.readAllLines(filePath);

            return lines.stream()
                    .map(participant ->{
                        try {

                            return participantMapper.readValue(participant,Participant.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("jaxson error");
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            System.out.println("could not read file");
            return List.of();

        }

    }


}
