package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public String saveContactRequest(@RequestBody ContactRequest request) {
        try {
            contactRepository.save(request); // 1. Instant save to MongoDB

            emailService.sendEmailNotification(request); // 2. Hands off to the background

            return "Success: Request stored and management notified!"; // 3. Instant reply to user
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Could not process request. " + e.getMessage();
        }
    }
}