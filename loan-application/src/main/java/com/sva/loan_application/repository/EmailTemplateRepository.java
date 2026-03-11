package com.sva.loan_application.repository;

import com.sva.loan_application.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    List<EmailTemplate> findByIsActiveTrue();
    Optional<EmailTemplate> findByName(String name);
    List<EmailTemplate> findByCategory(String category);
}