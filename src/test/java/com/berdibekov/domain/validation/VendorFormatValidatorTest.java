package com.berdibekov.domain.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class VendorFormatValidatorTest {

    ConstraintValidatorContext stubContext;
    VendorFormatValidator vendorFormatValidator;

    @BeforeEach
    void setUp() {
        vendorFormatValidator = new VendorFormatValidator();
    }

    @ParameterizedTest
    @CsvSource({"BMW-X5,true", "Mercedes-EQV Extra-long,true", "Mercedes glk,false", "BMW-,false", "-X5,false", "-,false",
                "--BMW-X5,false","BMW--X5,false"})
    void isValid(String modelAndVendor, boolean expected) {
        boolean result = vendorFormatValidator.isValid(modelAndVendor, stubContext);
        assertEquals(expected, result);
    }
}