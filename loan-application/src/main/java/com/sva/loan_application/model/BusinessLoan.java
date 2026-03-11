package com.sva.loan_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_loan_applications")
public class BusinessLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String businessName;
    private String businessType;
    private String businessIndustry;
    private String businessLoanStartDate;
    private String businessLoanAmount;
    private String loanPurpose;
    private String personalName;
    private String businessPhone;
    private String businessEmail;
    private String businessPincode;
    private String businessLoanCredit;
    
    private LocalDateTime submissionDate = LocalDateTime.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessIndustry() {
        return businessIndustry;
    }

    public void setBusinessIndustry(String businessIndustry) {
        this.businessIndustry = businessIndustry;
    }

    public String getBusinessLoanStartDate() {
        return businessLoanStartDate;
    }

    public void setBusinessLoanStartDate(String businessLoanStartDate) {
        this.businessLoanStartDate = businessLoanStartDate;
    }

    public String getBusinessLoanAmount() {
        return businessLoanAmount;
    }

    public void setBusinessLoanAmount(String businessLoanAmount) {
        this.businessLoanAmount = businessLoanAmount;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPincode() {
        return businessPincode;
    }

    public void setBusinessPincode(String businessPincode) {
        this.businessPincode = businessPincode;
    }

    public String getBusinessLoanCredit() {
        return businessLoanCredit;
    }

    public void setBusinessLoanCredit(String businessLoanCredit) {
        this.businessLoanCredit = businessLoanCredit;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}