package com.cms.japi.commons.validators.internal;

import com.cms.japi.commons.validators.ValidJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonValidator implements ConstraintValidator<ValidJson, String> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            objectMapper.readTree(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
