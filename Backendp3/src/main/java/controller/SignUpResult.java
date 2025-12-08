package controller;
import Classes.Participant;
public class SignUpResult {

    private final boolean success;
    private final String message;
    private final Participant participant;

    public SignUpResult(boolean success, String message, Participant participant) {
        this.success = success;
        this.message = message;
        this.participant = participant;
    }

    public static SignUpResult success(Participant p){

        return new SignUpResult (true, p.getFirstName()+ "er tilmeldt", p);
    }

    public static SignUpResult fail(String msg){

        return new SignUpResult(false,msg,null);
    }

    public static SignUpResult successWaitingList(Participant participant) {
        return new SignUpResult (true, "Du er nu på venteliste",participant);

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
}
