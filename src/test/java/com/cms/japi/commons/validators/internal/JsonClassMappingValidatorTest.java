package com.cms.japi.commons.validators.internal;

import com.cms.japi.commons.validators.ValidJsonClassMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

class JsonClassMappingValidatorTest {

    private JsonClassMappingValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new JsonClassMappingValidator(new ObjectMapper());
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void shouldReturnTrue() {
        // Arrange
        String validJson = "{\"field1\": \"value1\", \"field2\": 123}";

        validator.initialize(getAnnotation());

        // Act
        boolean isValid = validator.isValid(validJson, context);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseInvalidJsonSyntax() {
        // Arrange
        String invalidJson = "{\"field1\": \"value1\", \"field2\": 123"; // Missing closing brace

        validator.initialize(getAnnotation());

        // Act
        boolean isValid = validator.isValid(invalidJson, context);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseMismatchedFields() {
        // Arrange
        String json = "{\"field1\": \"value1\", \"field3\": 123}"; // field3 does not exist in the class

        validator.initialize(getAnnotation());

        // Act
        boolean isValid = validator.isValid(json, context);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldReturnFalseExtraFields() {
        // Arrange
        String json = "{\"field1\": \"value1\", \"field2\": 123, \"extraField\": true}";

        validator.initialize(getAnnotation());

        // Act
        boolean isValid = validator.isValid(json, context);

        // Assert
        assertFalse(isValid);
    }

    private static class TestTargetClass {
        public String field1;
        public int field2;
    }

    private static ValidJsonClassMapping getAnnotation() {
        return new ValidJsonClassMapping() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ValidJsonClassMapping.class;
            }

            @Override
            public String message() {
                return "";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<?> targetClass() {
                return TestTargetClass.class;
            }
        };
    }
}