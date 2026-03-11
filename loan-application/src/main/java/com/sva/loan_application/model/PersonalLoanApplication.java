package com.sva.loan_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

import com.sva.loan_application.validation.AdultAge;
import com.sva.loan_application.converter.AttributeEncryptor;

@Entity
@Table(name = "personal_loan_applications")
@Data
public class PersonalLoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Loan purpose is required")
    private String purpose;
    
    @NotNull(message = "Loan amount is required")
    @Min(value = 1000, message = "Loan amount must be at least 1000")
    @Max(value = 1000000, message = "Loan amount cannot exceed 1000000")
    private Double amount;
    
    @NotBlank(message = "Date of birth is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Date of birth must be in format DD/MM/YYYY")
    @AdultAge
    private String dob;
    
    @NotBlank(message = "Housing status is required")
    private String housingStatus;
    
    @NotBlank(message = "Credit score is required")
    private String creditScore;
    
    @NotBlank(message = "Employment status is required")
    private String employmentStatus;
    
    @NotNull(message = "Income is required")
    @Min(value = 0, message = "Income cannot be negative")
    private Double income;
    
    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;
    
    @NotBlank(message = "Address is required")
    @Convert(converter = AttributeEncryptor.class)
    private String address;
    
    @NotBlank(message = "Full name is required")
    @Convert(converter = AttributeEncryptor.class)
    private String fullName;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Convert(converter = AttributeEncryptor.class)
    private String phone;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Convert(converter = AttributeEncryptor.class)
    private String email;
    
    @Column(name = "application_date")
    private LocalDate applicationDate = LocalDate.now();
    
    @Column(name = "status")
    private String status = "PENDING";
    
    // Explicitly add getter and setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getDob() {
        return dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }
    
    public String getHousingStatus() {
        return housingStatus;
    }
    
    public void setHousingStatus(String housingStatus) {
        this.housingStatus = housingStatus;
    }
    
    public String getCreditScore() {
        return creditScore;
    }
    
    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }
    
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public Double getIncome() {
        return income;
    }
    
    public void setIncome(Double income) {
        this.income = income;
    }
    
    public String getPincode() {
        return pincode;
    }
    
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}