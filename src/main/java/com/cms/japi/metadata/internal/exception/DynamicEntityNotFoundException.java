package com.cms.japi.metadata.internal.exception;


public class DynamicEntityNotFoundException extends RuntimeException {

    public DynamicEntityNotFoundException() {
        super("Dynamic Entity Not Found");
    }
}
