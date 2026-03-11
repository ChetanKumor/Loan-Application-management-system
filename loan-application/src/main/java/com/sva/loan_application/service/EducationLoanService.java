package com.sva.loan_application.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sva.loan_application.dto.AdminLoanSummaryDTO;
import com.sva.loan_application.model.EducationLoanApplication;
import com.sva.loan_application.repository.EducationLoanRepository;

@Service
public class EducationLoanService {

    @Autowired
    private EducationLoanRepository educationLoanRepository;
    
    public String submitApplication(Map<String, Object> formData) {
        EducationLoanApplication application = new EducationLoanApplication();
        
        // Generate unique reference ID with year and random number
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String randomNum = String.format("%05d", (int)(Math.random() * 100000));
        String referenceId = "EDU-" + year + "-" + randomNum;
        application.setReferenceId(referenceId);
        
        // Set submission date with Indian timezone
        LocalDateTime submissionDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        application.setSubmissionDate(submissionDate);
        
        // Map form data to entity
        mapFormDataToEntity(application, formData);
        
        // Log the application data before saving
        System.out.println("Saving application with data:");
        System.out.println("Student Name: " + application.getStudentName());
        System.out.println("Student Age: " + application.getStudentAge());
        System.out.println("Phone Number: " + application.getPhoneNumber());
        System.out.println("Email: " + application.getEmail());
        System.out.println("Address: " + application.getStreetAddress() + ", " + application.getCity() + ", " + application.getState() + ", " + application.getPinCode());
        System.out.println("Education Level: " + application.getEducationLevel());
        System.out.println("Course Name: " + application.getCourseName());
        
        // Save to database
        educationLoanRepository.save(application);
        
        return referenceId;
    }
    
    @SuppressWarnings("unchecked")
    private void mapFormDataToEntity(EducationLoanApplication application, Map<String, Object> formData) {
        try {
            // Log the incoming data for debugging
            System.out.println("Received form data: " + formData);
            
            // First, check for direct field mappings in the root level
            processDirectFields(application, formData);
            
            // Then process any nested objects
            processNestedObjects(application, formData);
            
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error mapping form data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void processDirectFields(EducationLoanApplication application, Map<String, Object> formData) {
        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            // Log each key-value pair
            System.out.println("Processing key: " + key + ", value: " + value);
            
            // Parse JSON strings if needed
            if (value instanceof String && ((String) value).startsWith("{") && ((String) value).endsWith("}")) {
                try {
                    // Use Jackson for proper JSON parsing
                    ObjectMapper objectMapper = new ObjectMapper();
                    value = objectMapper.readValue((String) value, Map.class);
                    System.out.println("Parsed JSON object: " + value);
                } catch (Exception e) {
                    // Log the parsing error
                    System.err.println("Error parsing JSON: " + e.getMessage());
                    // Keep as string if parsing fails
                }
            }
            
            // Map personal information
            if (key.equals("student_name") || key.equals("fullName") || key.contains("student_name") || key.contains("fullName")) {
                application.setStudentName(value.toString());
                System.out.println("Set student name: " + value.toString());
            }
            
            if (key.equals("student_age") || key.equals("age") || key.contains("student_age") || key.contains("age")) {
                application.setStudentAge(value.toString());
                System.out.println("Set student age: " + value.toString());
            }
            
            if (key.equals("phone_number") || key.equals("phone") || key.contains("phone_number") || key.contains("phone")) {
                application.setPhoneNumber(value.toString());
                System.out.println("Set phone number: " + value.toString());
            }
            
            if (key.equals("email") || key.contains("email")) {
                application.setEmail(value.toString());
                System.out.println("Set email: " + value.toString());
            }
            
            // Map address information
            if (key.equals("street_address") || key.equals("streetAddress") || key.contains("street_address") || key.contains("streetAddress")) {
                application.setStreetAddress(value.toString());
                System.out.println("Set street address: " + value.toString());
            }
            
            if (key.equals("city") || key.contains("city")) {
                application.setCity(value.toString());
                System.out.println("Set city: " + value.toString());
            }
            
            if (key.equals("state") || key.contains("state")) {
                application.setState(value.toString());
                System.out.println("Set state: " + value.toString());
            }
            
            if (key.equals("pin_code") || key.equals("pincode") || key.equals("zipCode") || 
                key.contains("pin_code") || key.contains("pincode") || key.contains("zipCode")) {
                application.setPinCode(value.toString());
                System.out.println("Set pin code: " + value.toString());
            }
            
            // Map education details
            if (key.equals("education_level") || key.contains("education_level")) {
                application.setEducationLevel(value.toString());
                System.out.println("Set education level: " + value.toString());
            }
            
            if (key.equals("course_name") || key.contains("course_name")) {
                application.setCourseName(value.toString());
                System.out.println("Set course name: " + value.toString());
            }
            
            if (key.equals("course_duration") || key.contains("course_duration")) {
                application.setCourseDuration(value.toString());
                System.out.println("Set course duration: " + value.toString());
            }
            
            if (key.equals("education_location") || key.equals("institutionLocation") || 
                key.contains("education_location") || key.contains("institutionLocation")) {
                application.setEducationLocation(value.toString());
                System.out.println("Set education location: " + value.toString());
            }
            
            // Map financial details
            if (key.equals("total_course_cost") || key.equals("courseFee") || 
                key.contains("total_course_cost") || key.contains("courseFee")) {
                application.setTotalCourseCost(parseDoubleValue(value.toString()));
                System.out.println("Set total course cost: " + value.toString());
            }
            
            if (key.equals("loan_amount") || key.contains("loan_amount")) {
                application.setLoanAmount(parseDoubleValue(value.toString()));
                System.out.println("Set loan amount: " + value.toString());
            }
            
            if (key.equals("credit_score") || key.contains("credit_score")) {
                application.setCreditScore(value.toString());
                System.out.println("Set credit score: " + value.toString());
            }
            
            if (key.equals("existing_loans") || key.contains("existing_loans")) {
                application.setExistingLoans(value.toString());
                System.out.println("Set existing loans: " + value.toString());
            }
            
            if (key.equals("has_liabilities") || key.contains("has_liabilities")) {
                application.setHasLiabilities(value.toString());
                System.out.println("Set has liabilities: " + value.toString());
            }
            
            // Map guarantor details
            if (key.equals("guarantor_type") || key.contains("guarantor_type")) {
                application.setGuarantorType(value.toString());
                System.out.println("Set guarantor type: " + value.toString());
            }
            
            if (key.equals("guardian_relationship") || key.equals("guarantorRelationship") || 
                key.contains("guardian_relationship") || key.contains("guarantorRelationship")) {
                application.setGuardianRelationship(value.toString());
                System.out.println("Set guardian relationship: " + value.toString());
            }
            
            if (key.equals("guarantor_occupation") || key.contains("guarantor_occupation")) {
                application.setGuarantorOccupation(value.toString());
                System.out.println("Set guarantor occupation: " + value.toString());
            }
            
            if (key.equals("guarantor_income") || key.contains("guarantor_income")) {
                application.setGuarantorIncome(parseDoubleValue(value.toString()));
                System.out.println("Set guarantor income: " + value.toString());
            }
            
            // Map collateral details
            if (key.equals("provide_collateral") || key.contains("provide_collateral")) {
                application.setProvideCollateral(value.toString());
                System.out.println("Set provide collateral: " + value.toString());
            }
            
            if (key.equals("collateral_type") || key.contains("collateral_type")) {
                application.setCollateralType(value.toString());
                System.out.println("Set collateral type: " + value.toString());
            }
            
            if (key.equals("collateral_value") || key.contains("collateral_value")) {
                application.setCollateralValue(parseDoubleValue(value.toString()));
                System.out.println("Set collateral value: " + value.toString());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void processNestedObjects(EducationLoanApplication application, Map<String, Object> formData) {
        // Handle nested objects if they exist
        if (formData.containsKey("education_personal_info")) {
            Object personalInfoObj = formData.get("education_personal_info");
            if (personalInfoObj instanceof Map) {
                Map<String, Object> personalInfo = (Map<String, Object>) personalInfoObj;
                if (application.getStudentName() == null && personalInfo.containsKey("fullName")) {
                    application.setStudentName(personalInfo.get("fullName").toString());
                    System.out.println("Set student name from nested object: " + personalInfo.get("fullName").toString());
                }
                if (application.getStudentAge() == null && personalInfo.containsKey("age")) {
                    application.setStudentAge(personalInfo.get("age").toString());
                    System.out.println("Set student age from nested object: " + personalInfo.get("age").toString());
                }
                if (application.getPhoneNumber() == null && personalInfo.containsKey("phone")) {
                    application.setPhoneNumber(personalInfo.get("phone").toString());
                    System.out.println("Set phone number from nested object: " + personalInfo.get("phone").toString());
                }
                if (application.getEmail() == null && personalInfo.containsKey("email")) {
                    application.setEmail(personalInfo.get("email").toString());
                    System.out.println("Set email from nested object: " + personalInfo.get("email").toString());
                }
            }
        }
        
        if (formData.containsKey("education_address")) {
            Object addressObj = formData.get("education_address");
            if (addressObj instanceof Map) {
                Map<String, Object> address = (Map<String, Object>) addressObj;
                if (application.getStreetAddress() == null && address.containsKey("streetAddress")) {
                    application.setStreetAddress(address.get("streetAddress").toString());
                    System.out.println("Set street address from nested object: " + address.get("streetAddress").toString());
                }
                if (application.getCity() == null && address.containsKey("city")) {
                    application.setCity(address.get("city").toString());
                    System.out.println("Set city from nested object: " + address.get("city").toString());
                }
                if (application.getState() == null && address.containsKey("state")) {
                    application.setState(address.get("state").toString());
                    System.out.println("Set state from nested object: " + address.get("state").toString());
                }
                if (application.getPinCode() == null && address.containsKey("pincode")) {
                    application.setPinCode(address.get("pincode").toString());
                    System.out.println("Set pin code from nested object: " + address.get("pincode").toString());
                }
            }
        }
        
        // Process education details if in a nested object
        if (formData.containsKey("education_details")) {
            Object educationObj = formData.get("education_details");
            if (educationObj instanceof Map) {
                Map<String, Object> education = (Map<String, Object>) educationObj;
                if (application.getEducationLevel() == null && education.containsKey("educationLevel")) {
                    application.setEducationLevel(education.get("educationLevel").toString());
                    System.out.println("Set education level from nested object: " + education.get("educationLevel").toString());
                }
                if (application.getCourseName() == null && education.containsKey("courseName")) {
                    application.setCourseName(education.get("courseName").toString());
                    System.out.println("Set course name from nested object: " + education.get("courseName").toString());
                }
                if (application.getCourseDuration() == null && education.containsKey("courseDuration")) {
                    application.setCourseDuration(education.get("courseDuration").toString());
                    System.out.println("Set course duration from nested object: " + education.get("courseDuration").toString());
                }
                if (application.getEducationLocation() == null && education.containsKey("institutionLocation")) {
                    application.setEducationLocation(education.get("institutionLocation").toString());
                    System.out.println("Set education location from nested object: " + education.get("institutionLocation").toString());
                }
            }
        }
        
        // Process financial details if in a nested object
        if (formData.containsKey("financial_details")) {
            Object financialObj = formData.get("financial_details");
            if (financialObj instanceof Map) {
                Map<String, Object> financial = (Map<String, Object>) financialObj;
                if (application.getTotalCourseCost() == null && financial.containsKey("totalCourseCost")) {
                    application.setTotalCourseCost(parseDoubleValue(financial.get("totalCourseCost").toString()));
                    System.out.println("Set total course cost from nested object: " + financial.get("totalCourseCost").toString());
                }
                if (application.getLoanAmount() == null && financial.containsKey("loanAmount")) {
                    application.setLoanAmount(parseDoubleValue(financial.get("loanAmount").toString()));
                    System.out.println("Set loan amount from nested object: " + financial.get("loanAmount").toString());
                }
            }
        }
    }
    
    private Double parseDoubleValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            // Remove currency symbols, commas, and other non-numeric characters
            String cleanValue = value.replaceAll("[^0-9.]", "");
            return Double.parseDouble(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Retrieves all education loan applications
     * 
     * @return List of all applications
     */
    public List<EducationLoanApplication> getAllApplications() {
        return educationLoanRepository.findAll();
    }
    
    /**
     * Retrieves a specific application by its reference ID
     * 
     * @param referenceId The reference ID to search for
     * @return Optional containing the application if found
     */
    public Optional<EducationLoanApplication> getApplicationByReferenceId(String referenceId) {
        return educationLoanRepository.findByReferenceId(referenceId);
    }
    
    /**
     * Adds a loan application to the admin dashboard
     * 
     * @param loanData The loan data to add
     * @return The saved application
     */
    public EducationLoanApplication addLoanToAdmin(AdminLoanSummaryDTO loanData) {
        EducationLoanApplication application = new EducationLoanApplication();
        
        // Set basic information from DTO
        application.setStudentName(loanData.getApplicantName());
        application.setEmail(loanData.getEmail());
        application.setPhoneNumber(loanData.getPhone());
        application.setLoanAmount(loanData.getAmount() != null ? Double.parseDouble(loanData.getAmount()) : null);
        
        // Generate reference ID
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String randomNum = String.format("%05d", (int)(Math.random() * 100000));
        String referenceId = "EDU-" + year + "-" + randomNum;
        application.setReferenceId(referenceId);
        
        // Set submission date
        application.setSubmissionDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        
        // Save to database
        return educationLoanRepository.save(application);
    }
    
    /**
     * Updates the status of an application
     * 
     * @param referenceId The reference ID of the application to update
     * @param newStatus The new status to set
     * @return The updated application, or empty if not found
     */
    public Optional<EducationLoanApplication> updateApplicationStatus(String referenceId, String newStatus) {
        Optional<EducationLoanApplication> applicationOpt = educationLoanRepository.findByReferenceId(referenceId);
        
        if (applicationOpt.isPresent()) {
            EducationLoanApplication application = applicationOpt.get();
            application.setApplicationStatus(newStatus);
            return Optional.of(educationLoanRepository.save(application));
        }
        
        return Optional.empty();
    }
}