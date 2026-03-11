package com.sva.loan_application.service;

import com.sva.loan_application.model.LoanApplication;
import com.sva.loan_application.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public LoanApplication saveLoanApplication(Map<String, String> applicationData) {
        LoanApplication loanApplication = new LoanApplication();
        
        // Map localStorage keys to entity fields using setter methods
        loanApplication.setPurpose(applicationData.get("personalLoanPurpose"));
        loanApplication.setAmount(applicationData.get("personalLoanAmount"));
        loanApplication.setCreditScore(applicationData.get("personalLoanCreditScore"));
        loanApplication.setFirstName(applicationData.get("personalLoanFirstName"));
        loanApplication.setLastName(applicationData.get("personalLoanLastName"));
        loanApplication.setDateOfBirth(applicationData.get("personalLoanDOB"));
        loanApplication.setAddress(applicationData.get("personalLoanAddress"));
        loanApplication.setCity(applicationData.get("personalLoanCity"));
        loanApplication.setState(applicationData.get("personalLoanState"));
        loanApplication.setZipCode(applicationData.get("personalLoanZip"));
        loanApplication.setEmploymentStatus(applicationData.get("personalLoanEmployment"));
        loanApplication.setAnnualIncome(applicationData.get("personalLoanIncome"));
        loanApplication.setHousingStatus(applicationData.get("personalLoanHousing"));
        loanApplication.setPhoneNumber(applicationData.get("personalLoanPhone"));
        loanApplication.setEmail(applicationData.get("personalLoanEmail"));
        
        loanApplication.setCreatedAt(LocalDateTime.now());
        
        return loanApplicationRepository.save(loanApplication);
    }
}