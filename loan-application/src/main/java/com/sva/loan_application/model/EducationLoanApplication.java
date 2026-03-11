package com.sva.loan_application.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "education_loan_applications")
public class EducationLoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reference_id", unique = true, nullable = false)
    private String referenceId;
    
    // Personal Information
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "student_age")
    private String studentAge;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "street_address")
    private String streetAddress;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "pin_code")
    private String pinCode;
    
    // Education Details
    @Column(name = "education_level")
    private String educationLevel;
    
    @Column(name = "course_name")
    private String courseName;
    
    @Column(name = "course_duration")
    private String courseDuration;
    
    @Column(name = "education_location")
    private String educationLocation;
    
    // Financial Details
    @Column(name = "total_course_cost")
    private Double totalCourseCost;
    
    @Column(name = "loan_amount")
    private Double loanAmount;
    
    @Column(name = "credit_score")
    private String creditScore;
    
    @Column(name = "existing_loans")
    private String existingLoans;
    
    @Column(name = "has_liabilities")
    private String hasLiabilities;
    
    // Guarantor Details
    @Column(name = "guarantor_type")
    private String guarantorType;
    
    @Column(name = "guardian_relationship")
    private String guardianRelationship;
    
    @Column(name = "guarantor_occupation")
    private String guarantorOccupation;
    
    @Column(name = "guarantor_income")
    private Double guarantorIncome;
    
    // Collateral Details
    @Column(name = "provide_collateral")
    private String provideCollateral;
    
    @Column(name = "collateral_type")
    private String collateralType;
    
    @Column(name = "collateral_value")
    private Double collateralValue;
    
    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReferenceId() {
        return referenceId;
    }
    
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentAge() {
        return studentAge;
    }
    
    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getStreetAddress() {
        return streetAddress;
    }
    
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPinCode() {
        return pinCode;
    }
    
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    
    public String getEducationLevel() {
        return educationLevel;
    }
    
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getCourseDuration() {
        return courseDuration;
    }
    
    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }
    
    public String getEducationLocation() {
        return educationLocation;
    }
    
    public void setEducationLocation(String educationLocation) {
        this.educationLocation = educationLocation;
    }
    
    public Double getTotalCourseCost() {
        return totalCourseCost;
    }
    
    public void setTotalCourseCost(Double totalCourseCost) {
        this.totalCourseCost = totalCourseCost;
    }
    
    public Double getLoanAmount() {
        return loanAmount;
    }
    
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }
    
    public String getCreditScore() {
        return creditScore;
    }
    
    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }
    
    public String getExistingLoans() {
        return existingLoans;
    }
    
    public void setExistingLoans(String existingLoans) {
        this.existingLoans = existingLoans;
    }
    
    public String getHasLiabilities() {
        return hasLiabilities;
    }
    
    public void setHasLiabilities(String hasLiabilities) {
        this.hasLiabilities = hasLiabilities;
    }
    
    public String getGuarantorType() {
        return guarantorType;
    }
    
    public void setGuarantorType(String guarantorType) {
        this.guarantorType = guarantorType;
    }
    
    public String getGuardianRelationship() {
        return guardianRelationship;
    }
    
    public void setGuardianRelationship(String guardianRelationship) {
        this.guardianRelationship = guardianRelationship;
    }
    
    public String getGuarantorOccupation() {
        return guarantorOccupation;
    }
    
    public void setGuarantorOccupation(String guarantorOccupation) {
        this.guarantorOccupation = guarantorOccupation;
    }
    
    public Double getGuarantorIncome() {
        return guarantorIncome;
    }
    
    public void setGuarantorIncome(Double guarantorIncome) {
        this.guarantorIncome = guarantorIncome;
    }
    
    public String getProvideCollateral() {
        return provideCollateral;
    }
    
    public void setProvideCollateral(String provideCollateral) {
        this.provideCollateral = provideCollateral;
    }
    
    public String getCollateralType() {
        return collateralType;
    }
    
    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }
    
    public Double getCollateralValue() {
        return collateralValue;
    }
    
    public void setCollateralValue(Double collateralValue) {
        this.collateralValue = collateralValue;
    }
    
    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Column(name = "application_status")
    private String applicationStatus = "Pending"; // Default status

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}