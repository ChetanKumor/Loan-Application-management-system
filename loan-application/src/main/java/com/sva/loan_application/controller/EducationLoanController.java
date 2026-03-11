package com.sva.loan_application.controller;

import com.sva.loan_application.model.EducationLoanApplication;
import com.sva.loan_application.service.EducationLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/education-loan")
@CrossOrigin(origins = "*") // For development; restrict in production
public class EducationLoanController {

    @Autowired
    private EducationLoanService educationLoanService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitApplication(@RequestBody Map<String, Object> formData) {
        try {
            // Log the received data
            System.out.println("Controller received: " + formData);
            
            String referenceId = educationLoanService.submitApplication(formData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Education loan application submitted successfully");
            response.put("referenceId", referenceId);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the full stack trace
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to submit application: " + e.getMessage());
            errorResponse.put("exceptionType", e.getClass().getName());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/applications")
    public ResponseEntity<List<EducationLoanApplication>> getAllApplications() {
        try {
            List<EducationLoanApplication> applications = educationLoanService.getAllApplications();
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/applications/{referenceId}")
    public ResponseEntity<?> getApplicationByReferenceId(@PathVariable String referenceId) {
        try {
            Optional<EducationLoanApplication> application = educationLoanService.getApplicationByReferenceId(referenceId);
            
            if (application.isPresent()) {
                return ResponseEntity.ok(application.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Application not found with reference ID: " + referenceId);
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error retrieving application: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PutMapping("/applications/{referenceId}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable String referenceId,
            @RequestBody Map<String, String> statusUpdate) {
        
        try {
            Optional<EducationLoanApplication> updatedApplication = 
                educationLoanService.updateApplicationStatus(referenceId, statusUpdate.get("status"));
            
            if (updatedApplication.isPresent()) {
                return ResponseEntity.ok(updatedApplication.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Application not found with reference ID: " + referenceId);
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error updating application status: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}