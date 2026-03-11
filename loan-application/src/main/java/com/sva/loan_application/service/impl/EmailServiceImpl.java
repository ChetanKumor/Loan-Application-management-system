package com.sva.loan_application.service.impl;

import com.sva.loan_application.model.EmailTemplate;
import com.sva.loan_application.repository.EmailTemplateRepository;
import com.sva.loan_application.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateRepository templateRepository;

    @Override
    @Async // Ensures email sending is asynchronous, preventing UI freeze
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true indicates multipart message (for attachments), "UTF-8" for character encoding
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML content
            
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}", to, e);
            // Re-throw as RuntimeException to be caught by Spring's exception handling
            // and translated into an appropriate HTTP response (e.g., 500 Internal Server Error).
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendEmailWithTemplate(String to, String templateName, Map<String, String> placeholders) {
        Optional<EmailTemplate> templateOpt = templateRepository.findByName(templateName);
        
        if (templateOpt.isPresent()) {
            EmailTemplate template = templateOpt.get();
            String subject = template.getSubject();
            String content = template.getContent();
            
            // Replace placeholders in subject and content
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                subject = subject.replace(placeholder, entry.getValue());
                content = content.replace(placeholder, entry.getValue());
            }
            
            sendEmail(to, subject, content); // Call the core sendEmail method
        } else {
            logger.error("Email template not found: {}", templateName);
            throw new RuntimeException("Email template not found: " + templateName);
        }
    }

    @Override
    @Async // Ensures bulk email sending is asynchronous
    public void sendBulkEmails(List<String> recipients, String subject, String content) {
        for (String recipient : recipients) {
            sendEmail(recipient, subject, content); // Send individual email for each recipient
        }
    }

    @Override
    public void sendBulkEmailsWithTemplate(List<String> recipients, String templateName, 
                                          List<Map<String, String>> placeholdersList) {
        if (recipients.size() != placeholdersList.size()) {
            throw new IllegalArgumentException("Recipients list and placeholders list must be the same size for templated bulk emails");
        }
        
        for (int i = 0; i < recipients.size(); i++) {
            sendEmailWithTemplate(recipients.get(i), templateName, placeholdersList.get(i));
        }
    }

    @Override
    public List<EmailTemplate> getAllTemplates() {
        return templateRepository.findByIsActiveTrue();
    }

    @Override
    public EmailTemplate saveTemplate(EmailTemplate template) {
        return templateRepository.save(template);
    }
}
