package org.moviesdata.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = YearInFutureValidator.class)
public @interface YearInFuture {

    String message() default "Year is too far in future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int value();
}
