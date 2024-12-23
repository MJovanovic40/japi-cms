package com.cms.japi.generation.internal.exceptions;

import com.cms.japi.generation.internal.exceptions.throwables.ColumnDoesNotExistsException;
import com.cms.japi.generation.internal.exceptions.throwables.TableDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MigrationAdvice {

    @ResponseBody
    @ExceptionHandler(TableDoesNotExistsException.class)
    public ResponseEntity<String> tableDoesNotExistsHandler(TableDoesNotExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ColumnDoesNotExistsException.class)
    public ResponseEntity<String> columnDoesNotExistsHandler(ColumnDoesNotExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
