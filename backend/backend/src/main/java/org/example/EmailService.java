package org.example;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    // This securely pulls your API key from application.properties / Render
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Async
    public void sendEmailNotification(ContactRequest request) {

        // ⚠️ CRITICAL: The "from" email MUST be the exact Gmail address you just verified in SendGrid
        Email from = new Email("anilwhitecirclegroup@gmail.com");

        String subject = "New Contact Request: " + request.getCompanyName();

        // The management email where you want to receive the notifications
        Email to = new Email("nilesh@whitecirclegroup.in");

        String mailBody = "A new contact request has been submitted.\n\n" +
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Contact No: " + request.getContactNo() + "\n" +
                "Services: " + request.getServices() + "\n" +
                "Company Name: " + request.getCompanyName() + "\n\n" +
                "Message:\n" + request.getMessage();

        Content content = new Content("text/plain", mailBody);
        Mail mail = new Mail(from, subject, to, content);

        // This is where the magic happens: Sending via Enterprise API over standard web ports
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request apiRequest = new Request();
        try {
            apiRequest.setMethod(Method.POST);
            apiRequest.setEndpoint("mail/send");
            apiRequest.setBody(mail.build());

            Response response = sg.api(apiRequest);

            // This will print to your Render logs so you know it worked!
            System.out.println("SendGrid Status Code: " + response.getStatusCode());

        } catch (IOException ex) {
            System.err.println("Error sending email via SendGrid: " + ex.getMessage());
        }
    }
}