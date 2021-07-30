package com.berdibekov.domain.validation;

import com.berdibekov.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class BirthDateValidator {

    public void validate(LocalDate birthDate) {
        if (Period.between(birthDate, LocalDate.now()).getYears() < 0) {
            throw new ValidationException("Birth date can not be in future.");
        }
        if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
            throw new ValidationException("Age should be > 18.");
        }
    }
}
