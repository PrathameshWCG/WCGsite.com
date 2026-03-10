package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async // <--- This magic word tells Java to run this in the background!
    public void sendEmailNotification(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("your.email@gmail.com");
        message.setTo("admin.email@whitecirclegroup.in"); // Put the real management email here
        message.setSubject("New Contact Request: " + request.getCompanyName());

        String mailBody = "A new contact request has been submitted.\n\n" +
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Contact No: " + request.getContactNo() + "\n" +
                "Services: " + request.getServices() + "\n" +
                "Company Name: " + request.getCompanyName() + "\n\n" +
                "Message:\n" + request.getMessage();

        message.setText(mailBody);
        mailSender.send(message);
    }
}