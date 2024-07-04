package com.role;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.web.multipart.MultipartFile;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*@RestController
@CrossOrigin // Allow requests from all origins, you can adjust this according to your needs
public class AdmissionFormController {

    private final StudentRepository studentRepository;
    private final JavaMailSender emailSender;

    @Autowired
    public AdmissionFormController(StudentRepository studentRepository, JavaMailSender emailSender) {
        this.studentRepository = studentRepository;
        this.emailSender = emailSender;
    }

    @PostMapping("/admission-form")
    public ResponseEntity<String> submitForm(Student student, @RequestParam("file") MultipartFile file) throws IOException {
        // Handle form submission and file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
			String filePath = "/path/to/save/" + fileName; // Specify the file path
			student.setPhotoFileName(fileName); // Save file name in the database
			// Save the student entity to the database
			studentRepository.save(student);

			// Send email
			sendEmail(student.getEmail(), "Admission Form Submitted", "Your admission form has been submitted successfully.", student);


			return ResponseEntity.ok("Form submitted successfully. Email Sent Successfully");
        } else {
            return ResponseEntity.badRequest().body("File is empty.");
        }
    }

    /*private void sendEmail(String recipientEmail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }*/
    /*private void sendEmail(String recipientEmail, String subject, String message, Student student) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        
        // Construct the message body with student details
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Dear ").append(student.getFirstName()).append(",\n\n");
        messageBuilder.append("Your admission form has been submitted successfully.\n\n");
        messageBuilder.append("Student Details:\n");
        messageBuilder.append("Name: ").append(student.getFirstName()).append(" ").append(student.getMiddleName()).append(" ").append(student.getLastName()).append("\n");
        messageBuilder.append("Gender: ").append(student.getGender()).append("\n");
        messageBuilder.append("Date of Birth: ").append(student.getDob()).append("\n");
        messageBuilder.append("Admission to Class: ").append(student.getClassAdmission()).append("\n");
        messageBuilder.append("Parent/Guardian: ").append(student.getRelationship()).append("\n");
        messageBuilder.append("Parent First Name: ").append(student.getFatherFirstName()).append("\n");
        messageBuilder.append("Parent Last Name: ").append(student.getFatherLastName()).append("\n");
        messageBuilder.append("Email Address: ").append(student.getEmail()).append("\n");
        messageBuilder.append("Phone Number: ").append(student.getPhone()).append("\n");
        messageBuilder.append("Parent Occupation: ").append(student.getFatherOccupation()).append("\n");
        messageBuilder.append("Residential Address: ").append(student.getAddress()).append("\n");

        email.setText(messageBuilder.toString());
        emailSender.send(email);
    }*/
    


/*@RestController
@CrossOrigin // Allow requests from all origins, you can adjust this according to your needs
public class AdmissionFormController {

    private final StudentRepository studentRepository;
    private final JavaMailSender emailSender;
  

    @Autowired
    public AdmissionFormController(StudentRepository studentRepository, JavaMailSender emailSender) {
        this.studentRepository = studentRepository;
        this.emailSender = emailSender;
    }

    @PostMapping("/admission-form")
    public ResponseEntity<String> submitForm(Student student, @RequestParam("file") MultipartFile file) throws IOException, MessagingException {
        // Handle form submission and file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
			String filePath = "/path/to/save/" + fileName; // Specify the file path
			student.setPhotoFileName(fileName); // Save file name in the database
			// Save the student entity to the database
			studentRepository.save(student);

			// Send email with PDF
			sendEmailWithPDF(student.getEmail(), "Admission Form Submitted", "Your admission form has been submitted successfully.", student);

			return ResponseEntity.ok("Form submitted successfully. Email Sent Successfully. Please check your Inbox");
        } else {
            return ResponseEntity.badRequest().body("File is empty.");
        }
    }

    private void sendEmailWithPDF(String recipientEmail, String subject, String message, Student student) throws IOException, MessagingException {
        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Create content stream for writing to the PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Student Details:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getFirstName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getMiddleName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getLastName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Gender: " + student.getGender());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date of Birth: " + student.getDob());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Admission to Class: " + student.getClassAdmission());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent/Guardian: " + student.getRelationship());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent First Name: " + student.getFatherFirstName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent Last Name: " + student.getFatherLastName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email Address: " + student.getEmail());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Phone Number: " + student.getPhone());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent Occupation: " + student.getFatherOccupation());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Residential Address: " + student.getAddress());
                contentStream.endText();
            }
        
        // Save the document to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
         
         // Create DataSource from byte array
            DataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

         // Create the email message
            String message1 = "Dear " + student.getFirstName() + ",\n\nYour admission form has been submitted successfully.\n\n";

            // Attach the PDF to the email
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(message1);
            
            helper.addAttachment("Student_Details.pdf", dataSource);
            // Send the email
            emailSender.send(mimeMessage);
        }
    }


}*/


/*@RestController
@CrossOrigin // Allow requests from all origins, you can adjust this according to your needs
public class AdmissionFormController {

    private final StudentRepository studentRepository;
    private final JavaMailSender emailSender;

    @Autowired
    public AdmissionFormController(StudentRepository studentRepository, JavaMailSender emailSender) {
        this.studentRepository = studentRepository;
        this.emailSender = emailSender;
    }

    @PostMapping("/admission-form")
    public ResponseEntity<String> submitForm(Student student, @RequestParam("file") MultipartFile file) throws IOException, MessagingException {
        // Handle form submission and file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "/path/to/save/" + fileName; // Specify the file path
            student.setPhotoFileName(fileName); // Save file name in the database
            // Save the student entity to the database
            studentRepository.save(student);

            // Send email with PDF
            sendEmailWithPDF(student.getEmail(), "Admission Form Submitted", "Your admission form has been submitted successfully.", student);

            return ResponseEntity.ok("Form submitted successfully. Email Sent Successfully. Please check your Inbox");
        } else {
            return ResponseEntity.badRequest().body("File is empty.");
        }
    }

    private void sendEmailWithPDF(String recipientEmail, String subject, String message, Student student) throws IOException, MessagingException {
        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Create content stream for writing to the PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Student Details:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getFirstName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getMiddleName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + student.getLastName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Gender: " + student.getGender());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date of Birth: " + student.getDob());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Admission to Class: " + student.getClassAdmission());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent/Guardian: " + student.getRelationship());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent First Name: " + student.getFatherFirstName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent Last Name: " + student.getFatherLastName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email Address: " + student.getEmail());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Phone Number: " + student.getPhone());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Parent Occupation: " + student.getFatherOccupation());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Residential Address: " + student.getAddress());
                contentStream.endText();
            }

            // Save the document to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            // Create DataSource from byte array
            DataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

            // Create the email message
            String messageBody = "Dear " + student.getFirstName() + ",\n\nYour admission form has been submitted successfully.\n\n";

            // Create the MimeMessage
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setFrom("admissions@jslschool.com"); // Set the sender address here
            helper.setSubject(subject);
            helper.setText(messageBody);

            // Attach the PDF to the email
            helper.addAttachment("Student_Details.pdf", dataSource);

            // Send the email
            emailSender.send(mimeMessage);
        }
    }
}*/

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin // Allow requests from all origins, you can adjust this according to your needs
public class AdmissionFormController {

    private final StudentRepository studentRepository;
    private final JavaMailSender emailSender;

    @Autowired
    public AdmissionFormController(StudentRepository studentRepository, JavaMailSender emailSender) {
        this.studentRepository = studentRepository;
        this.emailSender = emailSender;
    }

    @PostMapping("/admission-form")
    public ResponseEntity<String> submitForm(Student student, @RequestParam("file") MultipartFile file) throws IOException, MessagingException {
        // Handle form submission and file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "/path/to/save/" + fileName; // Specify the file path
            student.setPhotoFileName(fileName); // Save file name in the database
            // Save the student entity to the database
            studentRepository.save(student);

            // Generate PDF with form data
            byte[] pdfBytes = generatePDF(student);

            // Send email with PDF
            sendEmailWithPDF(student.getEmail(), "Admission Form Submitted", "Your admission form has been submitted successfully.", pdfBytes);

            return ResponseEntity.ok("Form submitted successfully. Email Sent Successfully. Please check your Inbox");
        } else {
            return ResponseEntity.badRequest().body("File is empty.");
        }
    }

   
   
            private byte[] generatePDF(Student student) throws IOException {
                // Create a new PDF document
                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Create content stream for writing to the PDF
                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        // Set font and position for writing text
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(50, 700); // Set the starting position for text

                        // Write student details to the PDF
                        contentStream.showText("Student Details:");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Name: " + student.getFirstName() + " " + student.getLastName());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Gender: " + student.getGender());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Date of Birth: " + student.getDob());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Admission to Class: " + student.getClassAdmission());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Parent/Guardian: " + student.getRelationship());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Parent First Name: " + student.getFatherFirstName());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Parent Last Name: " + student.getFatherLastName());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Email Address: " + student.getEmail());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Phone Number: " + student.getPhone());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Parent Occupation: " + student.getFatherOccupation());
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Residential Address: " + student.getAddress());
                        // Add more fields as needed

                        // End text writing
                        contentStream.endText();
                    }

                    // Save the document to a byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    document.save(baos);
                    document.close();

                    return baos.toByteArray();
                }
            } 



    private void sendEmailWithPDF(String recipientEmail, String subject, String message, byte[] pdfBytes) throws MessagingException {
        // Create the email message
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(recipientEmail);
        helper.setFrom("admissions@jslschool.com"); // Set the sender address here
        helper.setSubject(subject);
        helper.setText(message);

        // Attach the PDF to the email
        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
        helper.addAttachment("Admission_Form.pdf", pdfResource);

        // Send the email
        emailSender.send(mimeMessage);
    }
}



