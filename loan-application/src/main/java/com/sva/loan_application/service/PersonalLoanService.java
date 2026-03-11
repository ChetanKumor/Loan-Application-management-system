package com.sva.loan_application.service;

import com.sva.loan_application.model.PersonalLoanApplication;
import com.sva.loan_application.repository.PersonalLoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PersonalLoanService {

    private final PersonalLoanApplicationRepository repository;

    @Autowired
    public PersonalLoanService(PersonalLoanApplicationRepository repository) {
        this.repository = repository;
    }

    public PersonalLoanApplication createApplicationFromFormData(Map<String, Object> formData) {
        // Validate required fields
        List<String> missingFields = new ArrayList<>();
        
        if (!formData.containsKey("purpose") || formData.get("purpose").toString().trim().isEmpty()) {
            missingFields.add("purpose");
        }
        
        if (!formData.containsKey("amount")) {
            missingFields.add("amount");
        } else {
            try {
                Double amount = Double.parseDouble(formData.get("amount").toString());
                if (amount < 1000 || amount > 1000000) {
                    throw new IllegalArgumentException("Loan amount must be between 1,000 and 1,000,000");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid loan amount format");
            }
        }
        
        // Add more validations for other fields
        
        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("Missing required fields: " + String.join(", ", missingFields));
        }
        
        // Create application if validation passes
        PersonalLoanApplication application = new PersonalLoanApplication();
        
        // Map form data to application fields
        if (formData.containsKey("purpose")) {
            application.setPurpose(formData.get("purpose").toString());
        }
        
        if (formData.containsKey("amount")) {
            try {
                Double amount = Double.parseDouble(formData.get("amount").toString());
                application.setAmount(amount);
            } catch (NumberFormatException e) {
                application.setAmount(0.0);
            }
        }
        
        if (formData.containsKey("dob")) {
            application.setDob(formData.get("dob").toString());
        }
        
        if (formData.containsKey("housingStatus")) {
            application.setHousingStatus(formData.get("housingStatus").toString());
        }
        
        if (formData.containsKey("creditScore")) {
            String creditScoreValue = formData.get("creditScore").toString();
            application.setCreditScore(creditScoreValue);
        }
        
        if (formData.containsKey("employmentStatus")) {
            application.setEmploymentStatus(formData.get("employmentStatus").toString());
        }
        
        if (formData.containsKey("income")) {
            try {
                application.setIncome(Double.parseDouble(formData.get("income").toString()));
            } catch (NumberFormatException e) {
                application.setIncome(0.0);
            }
        }
        
        if (formData.containsKey("pincode")) {
            application.setPincode(formData.get("pincode").toString());
        }
        
        if (formData.containsKey("address")) {
            application.setAddress(formData.get("address").toString());
        }
        
        if (formData.containsKey("fullName")) {
            application.setFullName(formData.get("fullName").toString());
        }
        
        if (formData.containsKey("phone")) {
            application.setPhone(formData.get("phone").toString());
        }
        
        if (formData.containsKey("email")) {
            application.setEmail(formData.get("email").toString());
        }
        
        application.setApplicationDate(LocalDate.now());
        application.setStatus("PENDING");
        
        return application;
    }

    public PersonalLoanApplication saveLoanApplication(PersonalLoanApplication application) {
        // Add debugging
        System.out.println("Saving application to database: " + application);
        try {
            PersonalLoanApplication saved = repository.save(application);
            System.out.println("Successfully saved application with ID: " + saved.getId());
            return saved;
        } catch (Exception e) {
            System.err.println("Error in service while saving application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<PersonalLoanApplication> getAllApplications() {
        System.out.println("Retrieving all applications from database");
        try {
            List<PersonalLoanApplication> applications = repository.findAll();
            System.out.println("Found " + applications.size() + " applications");
            return applications;
        } catch (Exception e) {
            System.err.println("Error in service while retrieving applications: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}