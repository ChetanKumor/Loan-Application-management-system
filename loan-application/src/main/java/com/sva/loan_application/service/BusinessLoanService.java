package com.sva.loan_application.service;

import com.sva.loan_application.model.BusinessLoan;
import com.sva.loan_application.repository.BusinessLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusinessLoanService {

    @Autowired
    private BusinessLoanRepository businessLoanRepository;
    
    public BusinessLoan saveBusinessLoan(Map<String, String> loanData) {
        BusinessLoan loan = new BusinessLoan();
        
        // Map the data from localStorage to the entity
        loan.setBusinessName(loanData.get("businessName"));
        loan.setBusinessType(loanData.get("businessType"));
        loan.setBusinessIndustry(loanData.get("businessIndustry"));
        loan.setBusinessLoanStartDate(loanData.get("businessLoanStartDate")); // Use the correct setter method
        loan.setBusinessLoanAmount(loanData.get("businessLoanAmount")); // Use the correct setter method
        loan.setLoanPurpose(loanData.get("loanPurpose"));
        loan.setPersonalName(loanData.get("personalName"));
        loan.setBusinessPhone(loanData.get("businessPhone"));
        loan.setBusinessEmail(loanData.get("businessEmail"));
        loan.setBusinessPincode(loanData.get("businessPincode"));
        loan.setBusinessLoanCredit(loanData.get("businessLoanCredit"));
        
        // Save to database
        return businessLoanRepository.save(loan);
    }
}