package com.berdibekov.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VendorFormatValidator implements ConstraintValidator<VendorFormatConstraint, String> {
    @Override
    public void initialize(VendorFormatConstraint constraint) {
    }

    @Override
    public boolean isValid(String vendorAndModel, ConstraintValidatorContext context) {
        String[] splitArray = vendorAndModel.split("-", 2);
        if (splitArray.length == 2) {
            String vendor = splitArray[0];
            String model = splitArray[1];
            if (vendor != null && model != null) {
                return !vendor.isEmpty() && !model.isBlank() && model.indexOf('-') != 0;
            }
        }
        return false;
    }
}
