package com.sva.loan_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sva.loan_application.model.EducationLoanApplication;

import java.util.Optional;

@Repository
public interface EducationLoanRepository extends JpaRepository<EducationLoanApplication, Long> {
    boolean existsByReferenceId(String referenceId);
    Optional<EducationLoanApplication> findByReferenceId(String referenceId);
}