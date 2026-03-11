package com.sva.loan_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String purpose;
    private String amount;
    private String creditScore;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String employmentStatus;
    private String annualIncome;
    private String housingStatus;
    private String phoneNumber;
    private String email;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getter for id
    public Long getId() {
        return id;
    }
    
    // Setters for all fields
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }
    
    public void setHousingStatus(String housingStatus) {
        this.housingStatus = housingStatus;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}