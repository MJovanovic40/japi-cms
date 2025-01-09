package com.cms.japi.commons.validators;

import com.cms.japi.commons.validators.internal.JsonValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonValidator.class)
public @interface ValidJson {

    String message() default "{javax.validation.constraints.json.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
