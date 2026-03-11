package com.sva.loan_application.service;

import com.sva.loan_application.model.MortgageLoanApplication;
import com.sva.loan_application.repository.MortgageLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MortgageLoanService {

    @Autowired
    private MortgageLoanRepository mortgageLoanRepository;
    
    // Define Indian time zone constant
    private static final ZoneId INDIAN_ZONE = ZoneId.of("Asia/Kolkata");

    /**
     * Submits a new mortgage loan application
     * 
     * @param applicationData Map containing application data from the frontend
     * @return The saved application with generated reference ID
     */
    public MortgageLoanApplication submitApplication(Map<String, String> applicationData) {
        try {
            MortgageLoanApplication application = new MortgageLoanApplication();
            
            // Generate a unique reference ID
            String referenceId = generateReferenceId();
            application.setReferenceId(referenceId);
            
            // Set submission date with Indian time zone
            application.setSubmissionDate(LocalDateTime.now(INDIAN_ZONE));
            
            // Map personal information
            application.setFirstName(applicationData.get("firstName"));
            application.setLastName(applicationData.get("lastName"));
            application.setFullName(applicationData.get("fullName"));
            application.setEmail(applicationData.get("email"));
            application.setPhone(applicationData.get("phone"));
            application.setDateOfBirth(applicationData.get("dateOfBirth"));
            application.setStreetAddress(applicationData.get("streetAddress"));
            application.setCity(applicationData.get("city"));
            application.setAddress(applicationData.get("address"));
            application.setEmploymentStatus(applicationData.get("employmentStatus"));
            
            // Map property information
            application.setPropertyType(applicationData.get("propertyType"));
            application.setPropertyUsage(applicationData.get("propertyUsage"));
            application.setPropertyPinCode(applicationData.get("propertyPinCode"));
            application.setPropertyValue(applicationData.get("propertyValue"));
            application.setPropertyStatus(applicationData.get("propertyStatus"));
            
            // Map loan details
            application.setLoanPurpose(applicationData.get("loanPurpose"));
            application.setLoanAmount(applicationData.get("loanAmount"));
            application.setCreditScore(applicationData.get("creditScore"));
            
            // Map application metadata
            application.setApplicationType(applicationData.get("applicationType"));
            application.setStatus("Pending");
            
            // Save to database
            return mortgageLoanRepository.save(application);
        } catch (Exception e) {
            System.err.println("Error saving mortgage loan application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Generates a unique reference ID for the application
     * Format: ML-YYYYMMDD-XXXX (ML for Mortgage Loan)
     * 
     * @return A unique reference ID
     */
    private String generateReferenceId() {
        // Use Indian time zone for correct local date
        LocalDateTime now = LocalDateTime.now(INDIAN_ZONE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = now.format(formatter);
        String randomPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4).toUpperCase();
        String referenceId = "ML-" + datePart + "-" + randomPart;
        
        // Check if reference ID already exists
        if (mortgageLoanRepository.existsByReferenceId(referenceId)) {
            // Try again with a different random part
            return generateReferenceId();
        }
        
        return referenceId;
    }
    
    /**
     * Retrieves all mortgage loan applications
     * 
     * @return List of all applications
     */
    public List<MortgageLoanApplication> getAllApplications() {
        return mortgageLoanRepository.findAll();
    }
    
    /**
     * Retrieves a specific application by its reference ID
     * 
     * @param referenceId The reference ID to search for
     * @return Optional containing the application if found
     */
    public Optional<MortgageLoanApplication> getApplicationByReferenceId(String referenceId) {
        return mortgageLoanRepository.findByReferenceId(referenceId);
    }
    
    /**
     * Retrieves applications by email address
     * 
     * @param email The email address to search for
     * @return List of applications with the given email
     */
    public List<MortgageLoanApplication> getApplicationsByEmail(String email) {
        return mortgageLoanRepository.findByEmail(email);
    }
    
    /**
     * Retrieves applications by applicant name (partial match)
     * 
     * @param name The name to search for
     * @return List of applications with matching names
     */
    public List<MortgageLoanApplication> getApplicationsByName(String name) {
        return mortgageLoanRepository.findByFullNameContainingIgnoreCase(name);
    }
    
    /**
     * Updates the status of an application
     * 
     * @param referenceId The reference ID of the application to update
     * @param newStatus The new status to set
     * @return The updated application, or empty if not found
     */
    public Optional<MortgageLoanApplication> updateApplicationStatus(String referenceId, String newStatus) {
        Optional<MortgageLoanApplication> applicationOpt = mortgageLoanRepository.findByReferenceId(referenceId);
        
        if (applicationOpt.isPresent()) {
            MortgageLoanApplication application = applicationOpt.get();
            application.setStatus(newStatus);
            return Optional.of(mortgageLoanRepository.save(application));
        }
        
        return Optional.empty();
    }
}