package org.moviesdata.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.moviesdata.constants.ImdbIdType;

import java.util.regex.Pattern;

public class ImdbIdValidator implements ConstraintValidator<ImdbId, String> {

    private ImdbIdType type;

    @Override
    public void initialize(ImdbId constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        if(id == null) return true;
        return Pattern.compile(type.name().toLowerCase()+"\\d{7}").matcher(id).find();
    }
}
