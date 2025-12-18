package Test;

import Controller.TLSEmailSender;

import java.util.ArrayList;
import java.util.List;

public class FakeTLSEmailSender implements TLSEmailSender {public String lastToEmail; public String lastBody;public String lastActivity;


            public static class Mail {
            public final String to;
            public final String body;
            public final String activity;

            public Mail(String to, String body, String activity) {
                this.to = to;
                this.body = body;
                this.activity = activity;
            }
        }

        public final List<Mail> sentMails = new ArrayList<>();

        @Override
        public void sendTLSMail(String toEmail, String body, String activity) {
            sentMails.add(new Mail(toEmail, body, activity));
        }
    }


