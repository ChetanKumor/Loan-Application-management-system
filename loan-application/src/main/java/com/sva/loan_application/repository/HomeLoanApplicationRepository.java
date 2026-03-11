package com.sva.loan_application.repository;

import com.sva.loan_application.model.HomeLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeLoanApplicationRepository extends JpaRepository<HomeLoanApplication, Long> {
}