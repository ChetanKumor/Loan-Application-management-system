package com.sva.loan_application.dto;

public class MortgageLoanDTO {
    // Property Information
    private String propertyType;
    private String loanUsage;
    private String propertyPincode;
    private String propertyValue;
    private String propertyStatus;
    
    // Loan Details
    private String loanPurpose;
    private String loanAmount;
    private String creditScore;
    
    // Personal Information
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String streetAddress;
    private String city;
    private String email;
    private String phone;
    private String employmentStatus;
    
    // Consent Information
    private boolean termsAgreed;
    private boolean privacyAgreed;
    private boolean communicationConsent;
    private boolean accuracyConfirmed;

    // Getters and Setters
    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getPropertyPincode() {
        return propertyPincode;
    }

    public void setPropertyPincode(String propertyPincode) {
        this.propertyPincode = propertyPincode;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public boolean isTermsAgreed() {
        return termsAgreed;
    }

    public void setTermsAgreed(boolean termsAgreed) {
        this.termsAgreed = termsAgreed;
    }

    public boolean isPrivacyAgreed() {
        return privacyAgreed;
    }

    public void setPrivacyAgreed(boolean privacyAgreed) {
        this.privacyAgreed = privacyAgreed;
    }

    public boolean isCommunicationConsent() {
        return communicationConsent;
    }

    public void setCommunicationConsent(boolean communicationConsent) {
        this.communicationConsent = communicationConsent;
    }

    public boolean isAccuracyConfirmed() {
        return accuracyConfirmed;
    }

    public void setAccuracyConfirmed(boolean accuracyConfirmed) {
        this.accuracyConfirmed = accuracyConfirmed;
    }
}