package Controller;

import Classes.Participant;

public class CancelResult {

    private final boolean success;
    private final String message;
    private final Participant participant;

    public CancelResult(boolean success, String message, Participant participant) {
        this.success = success;
        this.message = message;
        this.participant = participant;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Participant getParticipant() {
        return participant;
    }

    public CancelResult success(Participant p){
        return new CancelResult(true,"Deltager er afmeldt",p);
    }

    public CancelResult fail(String message){
        return new CancelResult(false,"Deltager kunne findes", null);

    }
}
