package com.cms.japi.metadata.internal.exceptions;

public class DynamicEntityExistsException extends RuntimeException {

    public DynamicEntityExistsException() {
        super("DynamicEntity already exists.");
    }
}
