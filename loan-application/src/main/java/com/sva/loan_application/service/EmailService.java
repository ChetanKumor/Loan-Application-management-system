package com.sva.loan_application.service;

import com.sva.loan_application.model.EmailTemplate;

import java.util.List;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void sendEmailWithTemplate(String to, String templateName, java.util.Map<String, String> placeholders);
    void sendBulkEmails(List<String> recipients, String subject, String content);
    void sendBulkEmailsWithTemplate(List<String> recipients, String templateName, List<java.util.Map<String, String>> placeholdersList);
    List<EmailTemplate> getAllTemplates();
    EmailTemplate saveTemplate(EmailTemplate template);
}