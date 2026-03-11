package com.sva.loan_application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AdultAgeValidator implements ConstraintValidator<AdultAge, String> {
    
    @Override
    public void initialize(AdultAge constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String dob, ConstraintValidatorContext context) {
        if (dob == null || dob.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Parse date in DD/MM/YYYY format
            String[] parts = dob.split("/");
            if (parts.length != 3) {
                return false;
            }
            
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate now = LocalDate.now();
            return Period.between(birthDate, now).getYears() >= 18;
        } catch (Exception e) {
            return false;
        }
    }
}