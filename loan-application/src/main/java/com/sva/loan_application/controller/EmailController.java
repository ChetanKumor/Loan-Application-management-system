package com.sva.loan_application.controller;

import com.sva.loan_application.model.EmailTemplate;
import com.sva.loan_application.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, Object> emailRequest) {
        String to = (String) emailRequest.get("to");
        String subject = (String) emailRequest.get("subject");
        String content = (String) emailRequest.get("content");
        
        emailService.sendEmail(to, subject, content);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/send-with-template")
    public ResponseEntity<String> sendEmailWithTemplate(@RequestBody Map<String, Object> request) {
        String to = (String) request.get("to");
        String templateName = (String) request.get("templateName");
        @SuppressWarnings("unchecked")
        Map<String, String> placeholders = (Map<String, String>) request.get("placeholders");
        
        emailService.sendEmailWithTemplate(to, templateName, placeholders);
        return ResponseEntity.ok("Email sent successfully using template");
    }

    @PostMapping("/send-bulk")
    public ResponseEntity<String> sendBulkEmails(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<String> recipients = (List<String>) request.get("recipients");
        String subject = (String) request.get("subject");
        String content = (String) request.get("content");
        
        emailService.sendBulkEmails(recipients, subject, content);
        return ResponseEntity.ok("Bulk emails sent successfully");
    }

    @PostMapping("/send-bulk-with-template")
    public ResponseEntity<String> sendBulkEmailsWithTemplate(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<String> recipients = (List<String>) request.get("recipients");
        String templateName = (String) request.get("templateName");
        @SuppressWarnings("unchecked")
        List<Map<String, String>> placeholdersList = (List<Map<String, String>>) request.get("placeholdersList");
        
        emailService.sendBulkEmailsWithTemplate(recipients, templateName, placeholdersList);
        return ResponseEntity.ok("Bulk emails sent successfully using template");
    }

    @GetMapping("/templates")
    public ResponseEntity<List<EmailTemplate>> getAllTemplates() {
        return ResponseEntity.ok(emailService.getAllTemplates());
    }

    @PostMapping("/templates")
    public ResponseEntity<EmailTemplate> saveTemplate(@RequestBody EmailTemplate template) {
        return ResponseEntity.ok(emailService.saveTemplate(template));
    }
}