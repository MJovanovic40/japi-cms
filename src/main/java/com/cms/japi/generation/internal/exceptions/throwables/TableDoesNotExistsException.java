package com.cms.japi.generation.internal.exceptions.throwables;

public class TableDoesNotExistsException extends RuntimeException {
    public TableDoesNotExistsException(String message) {
        super(message + " table does not exists.");
    }
}
