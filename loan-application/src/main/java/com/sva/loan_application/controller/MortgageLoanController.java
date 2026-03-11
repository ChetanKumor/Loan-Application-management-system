package com.sva.loan_application.controller;

import com.sva.loan_application.model.MortgageLoanApplication;
import com.sva.loan_application.service.MortgageLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MortgageLoanController {

    @Autowired
    private MortgageLoanService mortgageLoanService;

    @PostMapping("/mortgage-loan/submit")
    public ResponseEntity<Map<String, String>> submitApplication(@RequestBody Map<String, String> applicationData) {
        try {
            MortgageLoanApplication savedApplication = mortgageLoanService.submitApplication(applicationData);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Application submitted successfully");
            response.put("referenceId", savedApplication.getReferenceId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to submit application: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/mortgage-loan/{referenceId}")
    public ResponseEntity<?> getApplicationByReferenceId(@PathVariable String referenceId) {
        Optional<MortgageLoanApplication> application = mortgageLoanService.getApplicationByReferenceId(referenceId);
        
        if (application.isPresent()) {
            return ResponseEntity.ok(application.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Application not found with reference ID: " + referenceId);
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/mortgage-loan/all")
    public ResponseEntity<List<MortgageLoanApplication>> getAllApplications() {
        List<MortgageLoanApplication> applications = mortgageLoanService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
}