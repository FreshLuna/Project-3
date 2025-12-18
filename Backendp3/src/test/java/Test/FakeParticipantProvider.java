package Test;

import Classes.Participant;
import Controller.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeParticipantProvider implements ParticipantRepository {

    private final List<Participant> participants = new ArrayList<>();

    @Override
    public List<Participant> getParticipants(String activityName) {
        return participants.stream()
                .filter(p -> p.getActivity().equals(activityName))
                .toList();
    }

    @Override
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    @Override
    public boolean removeParticipant(Participant participant) {
        return participants.removeIf(p ->
                p.getFirstName().equals(participant.getFirstName()) &&
                        p.getLastName().equals(participant.getLastName()) &&
                        p.getEmail().equals(participant.getEmail()) &&
                        p.getActivity().equals(participant.getActivity())
        );
    }

    @Override
    public int indexOfParticipant(String activityName, Participant participant) {
        List<Participant> list = getParticipants(activityName);
        for (int i = 0; i < list.size(); i++) {
            Participant p = list.get(i);
            if (p.getEmail().equals(participant.getEmail())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean hasWaitingListPromotion(String activityName, int participantPosition, int capacity, boolean waitingListEnabled) {
        if (!waitingListEnabled) return false;
        return participantPosition >= 0 && participantPosition < capacity
                && getParticipants(activityName).size() > capacity;
    }

    @Override
    public Participant getPromotedParticipant(String activityName, int capacity) {
        List<Participant> list = getParticipants(activityName);
        if (list.size() > capacity) {
            return list.get(capacity);
        }
        return null;
    }

    @Override
    public List<Participant> handleWaitingListAfterCancel(String activityName, int capacity, boolean waitingListEnabled, int removedIndex) {
        List<Participant> list = getParticipants(activityName);
        if (!waitingListEnabled) return List.of();
        if (list.size() <= capacity) return List.of();
        return list.subList(capacity, Math.min(capacity + 3, list.size()));
    }
}
