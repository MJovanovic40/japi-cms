package com.cms.japi.generation.internal.exceptions.throwables;

public class ColumnDoesNotExistsException extends RuntimeException {
    public ColumnDoesNotExistsException(String message) {
        super(message + " column does not exists.");
    }
}
