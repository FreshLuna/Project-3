package Controller;

import Classes.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import Events.SignedUp;
import Events.Canceled;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static Config.FilePaths.EVENTS_FOLDER;

public class FileParticipantProvider implements ParticipantRepository{

    private final ObjectMapper mapper = new ObjectMapper()
    .setVisibility(
            com.fasterxml.jackson.annotation.PropertyAccessor.FIELD,
            com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
            );
    SignedUp signedUp = new SignedUp();
    Canceled canceled = new Canceled();

    @Override
    public List<Participant> getParticipants(String activityName) {
        Path filePath = Paths.get(
                EVENTS_FOLDER + activityName + "_users.txt"

        );

        List<Participant> participants = new ArrayList<>();


        if (!Files.exists(filePath)) {
            return participants; // empty list if file doesn't exist
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Participant participant = mapper.readValue(line, Participant.class);
                    participants.add(participant);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("JSON parsing error: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not read participant file: " + filePath);
        }
        return participants;
    }

    @Override
    public void addParticipant(Participant participant) {
        signedUp.appendParticipant(participantToString(participant));
    }

    @Override
    public boolean removeParticipant(Participant participant) {
        return canceled.removeParticipantByDetails(
                participant.getActivity(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail()

        );

    }


    @Override
    public int indexOfParticipant(String activityName, Participant participant) {

        List<Participant> participants = getParticipants(activityName);
        return IntStream.range(0, participants.size())
                .filter(i ->
                        participants.get(i).getFirstName().equalsIgnoreCase(participant.getFirstName()) &&
                                participants.get(i).getLastName().equalsIgnoreCase(participant.getLastName()) &&
                                participants.get(i).getEmail().equalsIgnoreCase(participant.getEmail())
                )
                .findFirst()
                .orElse(-1);
    }



    @Override
    public boolean hasWaitingListPromotion(String activityName, int participantPosition, int activityCapacity, boolean waitingListEnabled) {

        List<Participant> participants = getParticipants(activityName);
        boolean hasWaitingList = participants.size() >= activityCapacity;
        boolean participantNotOnWaitingList = participantPosition < activityCapacity;
        return waitingListEnabled && hasWaitingList && participantNotOnWaitingList;

    }
    @Override
    public Participant getPromotedParticipant(String activityName, int activityCapacity) {
        List<Participant> participants = getParticipants(activityName);
        if (participants.size() > activityCapacity) {
            return participants.get(activityCapacity-1);
        }
        return null; // no one to promote
    }



    @Override
    public List<Participant> handleWaitingListAfterCancel(String activityName,int activityCapacity,boolean waitingListEnabled,int removedIndex
    ) {
        List<Participant> participants = getParticipants(activityName);

        if (!waitingListEnabled) return List.of();
        if (participants.size() < activityCapacity) return List.of();
        if (removedIndex >= activityCapacity) return List.of();

        int from = activityCapacity;
        int to = Math.min(activityCapacity + 3, participants.size());

        return participants.subList(from, to);
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

