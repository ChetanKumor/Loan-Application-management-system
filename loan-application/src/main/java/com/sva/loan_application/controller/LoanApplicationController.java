package com.sva.loan_application.controller;

import com.sva.loan_application.model.LoanApplication;
import com.sva.loan_application.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping("/loan-applications")
    public ResponseEntity<?> createLoanApplication(@RequestBody Map<String, String> applicationData) {
        try {
            LoanApplication savedApplication = loanApplicationService.saveLoanApplication(applicationData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Application submitted successfully");
            response.put("applicationId", savedApplication.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to save application: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}