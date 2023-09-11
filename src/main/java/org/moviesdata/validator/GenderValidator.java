package org.moviesdata.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.moviesdata.constants.GenderEnum;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public void initialize(Gender constraintAnnotation) {}

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        if(gender == null) return true;

        for(GenderEnum possibleGender: GenderEnum.values()) {
            if(possibleGender.name().toLowerCase().equals(gender)) return true;
        }
        return false;
    }
}
