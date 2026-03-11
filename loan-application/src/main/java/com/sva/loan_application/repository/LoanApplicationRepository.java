package com.sva.loan_application.repository;

import com.sva.loan_application.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    // You can add custom query methods here if needed
}