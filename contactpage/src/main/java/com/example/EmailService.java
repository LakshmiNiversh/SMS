package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, ContactForm form, String companyName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);

        String text = String.format(
            "Dear %s,\n\n" +
            "Thank you for contacting us. We have received your message and will get back to you shortly.\n\n" +
            "Here is a summary of your submission:\n\n" +
            "Parent Name: %s\n" +
            "Email Address: %s\n" +
            "Phone Number: %s\n" +
            "Student Name: %s\n" +
            "Student Class: %s\n" +
            "Date of Birth: %s\n" +
            "Message: %s\n\n" +
            "Best regards,\n%s", // Placeholder for company name
            form.getParentName(), form.getParentName(), form.getEmailAddress(), form.getPhoneNumber(),
            form.getStudentName(),form.getStudentClass(),
            form.getDateOfBirth(), form.getMessage(),
            companyName // Inject company name here
        );

        message.setText(text);
        message.setFrom("admin@jslschool.com"); // use the correct sender email
        mailSender.send(message);
    }
}
