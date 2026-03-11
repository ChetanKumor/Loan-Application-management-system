package com.sva.loan_application.controller;

import com.sva.loan_application.model.PersonalLoanApplication;
import com.sva.loan_application.service.PersonalLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personal-loan")
@CrossOrigin(origins = "*") // For development; restrict in production
public class PersonalLoanController {

    private final PersonalLoanService loanService;

    @Autowired
    public PersonalLoanController(PersonalLoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> submitApplication(@Valid @RequestBody PersonalLoanApplication application, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        // Process valid application
        PersonalLoanApplication savedApplication = loanService.saveLoanApplication(application);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<PersonalLoanApplication>> getAllApplications() {
        try {
            List<PersonalLoanApplication> applications = loanService.getAllApplications();
            System.out.println("Retrieved " + applications.size() + " applications");
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error retrieving applications: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}