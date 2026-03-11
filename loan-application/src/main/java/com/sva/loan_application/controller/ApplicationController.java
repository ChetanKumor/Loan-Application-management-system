package com.sva.loan_application.controller;

import com.sva.loan_application.model.Application;
import com.sva.loan_application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Get all applications for the admin dashboard
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // Get application by ID or reference ID
    @GetMapping("/applications/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable String id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update application status
    @PutMapping("/applications/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> statusUpdate) {
        
        String status = statusUpdate.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return applicationService.updateApplicationStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Save application notes
    @PostMapping("/applications/{id}/notes")
    public ResponseEntity<Application> saveApplicationNotes(
            @PathVariable String id,
            @RequestBody Map<String, String> notesUpdate) {
        
        String notes = notesUpdate.get("notes");
        if (notes == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return applicationService.saveApplicationNotes(id, notes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new application (used by the education loan form)
    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application savedApplication = applicationService.saveApplication(application);
        return ResponseEntity.ok(savedApplication);
    }
}