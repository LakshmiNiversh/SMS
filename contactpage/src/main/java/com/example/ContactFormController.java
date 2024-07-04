package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactFormController {

    @Autowired
    private ContactFormService service;

    @Autowired
    private EmailService emailService;
@CrossOrigin("*")
    @PostMapping("/Contact-Us")
    public ContactForm submitForm(@RequestBody ContactForm form) {
        ContactForm savedForm = service.saveContactForm(form);

        // Send confirmation email with form data
        String to = form.getEmailAddress();
        String subject = "Contact Form Submission Confirmation";
        String companyName = "Jagadeeswari School of Learning"; // Replace with your actual company name

        emailService.sendEmail(to, subject, form, companyName);

        return savedForm;
    }
}