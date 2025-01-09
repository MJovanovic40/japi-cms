package com.cms.japi.commons.validators.internal;

import com.cms.japi.commons.validators.ValidJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

class JsonValidatorTest {

    private JsonValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new JsonValidator(new ObjectMapper());
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
    void shouldReturnFalse() {
        // Arrange
        String validJson = "{\"field1\": \"value1\", \"field2\": 123";

        validator.initialize(getAnnotation());

        // Act
        boolean isValid = validator.isValid(validJson, context);

        // Assert
        assertFalse(isValid);
    }

    private static ValidJson getAnnotation() {
        return new ValidJson() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ValidJson.class;
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

        };
    }

}