package com.berdibekov.domain.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatValidatorTest {

    @Mock
    ConstraintValidatorContext stub;

    String dateFormatPattern = "dd.MM.yyyy";

    DateFormatValidator dateFormatValidator;

    @BeforeEach
    void setUp() {
        dateFormatValidator = new DateFormatValidator();
        dateFormatValidator.setDateFormatPattern(dateFormatPattern);
    }

    @ParameterizedTest
    @CsvSource({"12.12.2020,true", "12-12-2020,false", "50.12.2020,false", "12.13.2020,false", "50.50.2h20,false",",false"})
    void isValid(String input, boolean expected) {
        assertEquals(expected, dateFormatValidator.isValid(input, stub));
    }
}