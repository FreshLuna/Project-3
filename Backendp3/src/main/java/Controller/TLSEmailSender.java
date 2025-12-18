package Controller;

public interface TLSEmailSender {

    void sendTLSMail(String toEmail, String body, String activity);
}
