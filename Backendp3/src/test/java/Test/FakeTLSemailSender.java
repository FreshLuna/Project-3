package Test;

import Controller.TLSEmailSender;

public class FakeTLSemailSender implements TLSEmailSender {
    public String lastToEmail;
    public String lastBody;
    public String lastActivity;
    @Override
    public void sendTLSMail(String toEmail, String body, String activity) {
        this.lastToEmail = toEmail;
        this.lastBody = body;
        this.lastActivity = activity;
    }
}
