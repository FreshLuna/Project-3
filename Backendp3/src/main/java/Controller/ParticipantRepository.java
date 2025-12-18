package Controller;

import Classes.Participant;

import java.util.List;

public interface ParticipantRepository {
        List<Participant> getParticipants(String activityName);
        void addParticipant(Participant participant);
        boolean removeParticipant(Participant participant);
        int indexOfParticipant(String activityName, Participant participant);
        boolean hasWaitingListPromotion(String activityName, int participantPosition, int activityCapacity, boolean waitingListEnabled);
        Participant getPromotedParticipant(String activityName, int activityCapacity);
        List<Participant> handleWaitingListAfterCancel(String activityName,int activityCapacity, boolean waitingListEnabled,int removedParticipantIndex);

}
