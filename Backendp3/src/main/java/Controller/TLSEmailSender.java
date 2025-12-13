package Controller;


import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class TLSEmailSender {


    public static void sendTLSMail(String toEmail, String body, String activity) {
        Dotenv dotenv = Dotenv.load();
        String MAIL = dotenv.get("MAIL");
        String PASSWORD = dotenv.get("PASSWORD");

        final String fromEmail = MAIL; //requires valid gmail id
        final String password = PASSWORD; // correct password for gmail id

        //System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail,"Tilmeldning til: " + activity, body);

    }
    public static void main(String[] args) {
        // Replace this with the email you want to send to
        String toEmail = "mwsandager@gmail.com";

        System.out.println("Sending test email to: " + toEmail);

        // Call the TLS email sender
        TLSEmailSender.sendTLSMail(toEmail, "sagdujsahjdgshjakgfkjSAGFJHKSAGFsaJFGSAJGFjhkaSGFJKHG", "Vild Med Dans");

        System.out.println("Test script finished.");
    }

}
