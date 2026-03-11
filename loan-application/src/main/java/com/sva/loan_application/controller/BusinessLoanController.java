package com.sva.loan_application.controller;

import com.sva.loan_application.model.BusinessLoan;
import com.sva.loan_application.service.BusinessLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/business-loan")
public class BusinessLoanController {

    @Autowired
    private BusinessLoanService businessLoanService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitBusinessLoan(@RequestBody Map<String, String> loanData) {
        try {
            BusinessLoan savedLoan = businessLoanService.saveBusinessLoan(loanData);
            
            // Format the submission date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
            String formattedSubmissionDate = savedLoan.getSubmissionDate().format(formatter);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Business loan application submitted successfully");
            response.put("applicationId", savedLoan.getId());
            response.put("submissionDateTime", formattedSubmissionDate);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to submit application: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}