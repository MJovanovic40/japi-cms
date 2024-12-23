package com.cms.japi.metadata.internal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DynamicEntityExceptionHandler {

    @ExceptionHandler(DynamicEntityNotFoundException.class)
    public ResponseEntity<Object> dynamicEntityNotFoundExceptionHandler(DynamicEntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
