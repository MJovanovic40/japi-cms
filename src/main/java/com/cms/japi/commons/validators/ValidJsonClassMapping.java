package com.cms.japi.commons.validators;

import com.cms.japi.commons.validators.internal.JsonClassMappingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonClassMappingValidator.class)
public @interface ValidJsonClassMapping {

    String message() default "{javax.validation.constraints.json.object.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> targetClass();
}
