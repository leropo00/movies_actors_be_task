package org.moviesdata.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.moviesdata.constants.ImdbIdType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ImdbIdValidator.class)
public @interface ImdbId {

    String message() default "Invalid Imdb id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    ImdbIdType type();
}
