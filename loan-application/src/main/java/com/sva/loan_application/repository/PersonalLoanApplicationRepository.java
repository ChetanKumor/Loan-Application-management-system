package com.sva.loan_application.repository;

import com.sva.loan_application.model.PersonalLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonalLoanApplicationRepository extends JpaRepository<PersonalLoanApplication, Long> {
    // Find applications by status
    List<PersonalLoanApplication> findByStatus(String status);
    
    // Find applications by email
    List<PersonalLoanApplication> findByEmail(String email);
    
    // Find applications by application date range
    List<PersonalLoanApplication> findByApplicationDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find applications by loan amount greater than a specified value
    List<PersonalLoanApplication> findByAmountGreaterThan(Double amount);
    
    // Custom query to find applications by partial name match
    @Query("SELECT p FROM PersonalLoanApplication p WHERE p.fullName LIKE %:name%")
    List<PersonalLoanApplication> findByPartialName(@Param("name") String name);
}