package com.berdibekov.domain.validation;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatValidator implements ConstraintValidator<BirthDateConstraint, String> {

    String message = "Invalid date format. Should be : ";

    @Value("${dateFormatPattern}")
    @Setter(AccessLevel.PACKAGE)
    private String dateFormatPattern;

    @Override
    public boolean isValid(String text, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(text, DateTimeFormatter.ofPattern(dateFormatPattern));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    String message() {
        return message + dateFormatPattern;
    }

    @Override
    public void initialize(BirthDateConstraint constraintAnnotation) {
    }
}
