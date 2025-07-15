/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import javax.mail.Authenticator;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class Email {
    private final int LIMIT_MINUS = 15;
    private final String eFrom = "lackguku1@gmail.com";
    private final String ePass = ""; // Your password
    
    public String createToken() {
        return UUID.randomUUID().toString();
    }
    
    public LocalDateTime expireToken() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }
    
    public boolean isExpireToken(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }
    
    public void sendEmail(String to, String subject, String content) {

        // Properties
        Properties props = new Properties();

        //Su dung server nao de gui mail- smtp host
        props.put("mail.smtp.host", "smtp.gmail.com");

        // TLS 587 SSL 465
        props.put("mail.smtp.port", "587");

        // dang nhap
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

        //dang nhap tai khoan
        Authenticator au = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(eFrom, ePass);
            }

        };
        // phien lam viec
        Session session = Session.getInstance(props, au);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML, charset=UTF-8");
            msg.setFrom(new InternetAddress(eFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // tieu de
            msg.setSubject(subject, "UTF-8");
            // Noi dung
            msg.setContent(content, "text/html; charset=UTF-8");
            // Gui email
            Transport.send(msg);
        } catch (MessagingException e) {
            System.out.println("Send email failed");
            e.printStackTrace();
        }
    }
    
    public String generateRandomSixDigits() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomDigit = random.nextInt(10); // Generate a number between 0 and 9
            result.append(randomDigit); // Append to the result
        }

        return result.toString();
    }
   
}
