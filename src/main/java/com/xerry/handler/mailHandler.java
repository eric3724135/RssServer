package com.xerry.handler;

import com.xerry.model.FeedMsg;
import com.xerry.model.RegInfo;
import org.springframework.context.annotation.PropertySource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by wwp on 2016/5/21.
 */
public class MailHandler {


    private static MailHandler ourInstance = new MailHandler();

    public static MailHandler getInstance() {
        return ourInstance;
    }

    private Session session;
    private String username;
    private String password;

    private MailHandler() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        username = properties.getProperty("mail.username");
        password = properties.getProperty("mail.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

    }

    public void send(RegInfo info, FeedMsg msg) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(info.getMail()));
            message.setSubject("[" + msg + "]" + msg.getTitle());
            message.setText("Notification,"
                    + "\n\n" + msg.getLink());

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        MailHandler.getInstance().send(null, null);
    }

}
