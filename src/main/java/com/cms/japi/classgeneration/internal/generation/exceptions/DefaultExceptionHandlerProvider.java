package com.cms.japi.classgeneration.internal.generation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultExceptionHandlerProvider {

    public ResponseEntity<Object> handleDynamicException(DynamicException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
