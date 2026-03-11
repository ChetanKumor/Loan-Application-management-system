package com.sva.loan_application.repository;

import com.sva.loan_application.model.BusinessLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLoanRepository extends JpaRepository<BusinessLoan, Long> {
}