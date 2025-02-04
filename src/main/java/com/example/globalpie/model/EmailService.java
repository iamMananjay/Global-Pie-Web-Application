package com.example.globalpie.model;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String firstName, String lastName, String email, String phone, String address, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("Mananjaysth111@gmail.com"); // Set the "From" address as the user's submitted email
        helper.setReplyTo(email);

        helper.setTo("Mananjaysth111@gmail.com"); // Replace with your company email
        helper.setSubject(subject);
        helper.setText("Name: " + firstName + " " + lastName + "\nEmail: " + email + "\nPhone: " + phone + "\nAddress: " + address + "\n\nMessage:\n" + message);

        mailSender.send(mimeMessage); // Send the email


        try {
            // Send an auto-reply to the user
            sendAutoReplyToUser(email);  // Send auto-reply after initial email
        } catch (MessagingException e) {
            e.printStackTrace();  // Handle exception (e.g., log it)
        }
    }

    // Send an auto-reply to the user
    public void sendAutoReplyToUser(String userEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("Mananjaysth111@gmail.com"); // Your email
        helper.setTo(userEmail); // Send reply to the user
        helper.setSubject("Thank You for Contacting Us!");
        helper.setText("Dear User, we have received your message. We will get back to you soon.");

        mailSender.send(message);
    }
}

