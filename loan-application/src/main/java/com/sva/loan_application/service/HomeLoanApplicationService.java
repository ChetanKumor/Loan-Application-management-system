package com.sva.loan_application.service;

import com.sva.loan_application.model.HomeLoanApplication;
import com.sva.loan_application.repository.HomeLoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeLoanApplicationService {

    @Autowired
    private HomeLoanApplicationRepository homeLoanApplicationRepository;

    public HomeLoanApplication saveHomeLoanApplication(HomeLoanApplication homeLoanApplication) {
        return homeLoanApplicationRepository.save(homeLoanApplication);
    }
}