package com.example.globalpie.controller;

import com.example.globalpie.dto.ContactRequest;
import com.example.globalpie.model.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendContactEmail(@RequestBody ContactRequest request) throws MessagingException { // Declare MessagingException here
        emailService.sendEmail(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhone(), request.getAddress(), request.getSubject(), request.getMessage());
        return "Message sent successfully!";
    }
}
