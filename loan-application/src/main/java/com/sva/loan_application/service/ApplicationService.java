package com.sva.loan_application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sva.loan_application.model.Application;
import com.sva.loan_application.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    // Get all applications
    public List<Application> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        
        // Convert detailsJson to details object for each application
        return applications.stream()
                .map(this::convertJsonToDetails)
                .collect(Collectors.toList());
    }

    // Get application by ID or reference ID
    public Optional<Application> getApplicationById(String id) {
        Optional<Application> applicationOpt;
        
        try {
            Long longId = Long.parseLong(id);
            applicationOpt = applicationRepository.findById(longId);
        } catch (NumberFormatException e) {
            // If id is not a number, try to find by reference ID
            applicationOpt = applicationRepository.findByReferenceId(id);
        }
        
        return applicationOpt.map(this::convertJsonToDetails);
    }

    // Update application status
    public Optional<Application> updateApplicationStatus(String id, String status) {
        Optional<Application> applicationOpt = getApplicationById(id);
        
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            application.setStatus(status);
            return Optional.of(convertJsonToDetails(applicationRepository.save(application)));
        }
        
        return Optional.empty();
    }

    // Save application notes
    public Optional<Application> saveApplicationNotes(String id, String notes) {
        Optional<Application> applicationOpt = getApplicationById(id);
        
        if (applicationOpt.isPresent()) {
            Application application = applicationOpt.get();
            application.setNotes(notes);
            return Optional.of(convertJsonToDetails(applicationRepository.save(application)));
        }
        
        return Optional.empty();
    }

    // Save a new application
    public Application saveApplication(Application application) {
        // Set default values if not provided
        if (application.getSubmittedDate() == null) {
            application.setSubmittedDate(LocalDateTime.now());
        }
        
        if (application.getStatus() == null) {
            application.setStatus("new");
        }
        
        // Convert details to JSON
        if (application.getDetails() != null) {
            try {
                application.setDetailsJson(objectMapper.writeValueAsString(application.getDetails()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting details to JSON", e);
            }
        }
        
        return convertJsonToDetails(applicationRepository.save(application));
    }
    
    // Helper method to convert detailsJson to details object
    private Application convertJsonToDetails(Application application) {
        if (application.getDetailsJson() != null && !application.getDetailsJson().isEmpty()) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> details = objectMapper.readValue(application.getDetailsJson(), Map.class);
                application.setDetails(details);
            } catch (JsonProcessingException e) {
                // Log error but continue
                System.err.println("Error converting JSON to details: " + e.getMessage());
            }
        }
        return application;
    }
}