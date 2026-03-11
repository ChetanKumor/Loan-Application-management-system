package com.sva.loan_application.repository;


import com.sva.loan_application.model.MortgageLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MortgageLoanRepository extends JpaRepository<MortgageLoanApplication, Long> {
    Optional<MortgageLoanApplication> findByReferenceId(String referenceId);
    boolean existsByReferenceId(String referenceId);
    List<MortgageLoanApplication> findByEmail(String email);
    List<MortgageLoanApplication> findByFullNameContainingIgnoreCase(String name);
}