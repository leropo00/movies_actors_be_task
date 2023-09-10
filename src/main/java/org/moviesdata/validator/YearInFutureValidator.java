package org.moviesdata.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class YearInFutureValidator implements ConstraintValidator<YearInFuture, Integer> {

    private int value;

    @Override
    public void initialize(YearInFuture constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        if(year == null) return true;
        if(year - LocalDate.now().getYear() > value )  return false;
        return true;
    }
}
