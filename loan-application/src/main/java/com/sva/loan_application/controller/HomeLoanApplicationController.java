package com.sva.loan_application.controller;

import com.sva.loan_application.model.HomeLoanApplication;
import com.sva.loan_application.service.HomeLoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeLoanApplicationController {

    @Autowired
    private HomeLoanApplicationService homeLoanApplicationService;

    @PostMapping("/home-loan-applications")
    public ResponseEntity<?> submitHomeLoanApplication(@RequestBody HomeLoanApplication homeLoanApplication) {
        HomeLoanApplication savedApplication = homeLoanApplicationService.saveHomeLoanApplication(homeLoanApplication);
        return ResponseEntity.ok(savedApplication);
    }
}