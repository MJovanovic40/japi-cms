package com.cms.japi.commons.validators.internal;

import com.cms.japi.commons.validators.ValidJsonClassMapping;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class JsonClassMappingValidator implements ConstraintValidator<ValidJsonClassMapping, String> {

    private Class<?> targetClass;
    private final ObjectMapper objectMapper;

    @Override
    public void initialize(ValidJsonClassMapping constraintAnnotation) {
        this.targetClass = constraintAnnotation.targetClass();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            JsonNode jsonNode = objectMapper.readTree(s);

            Set<String> classFieldNames = getFieldNames(targetClass);

            Set<String> jsonFieldNames = new HashSet<>();
            jsonNode.fieldNames().forEachRemaining(jsonFieldNames::add);

            if (!classFieldNames.equals(jsonFieldNames)) {
                return false;
            }

            objectMapper.readValue(s, targetClass);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<String> getFieldNames(Class<?> clazz) {
        Set<String> fieldNames = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }
}
