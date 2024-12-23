package com.cms.japi.metadata.internal.exceptions;


public class DynamicEntityNotFoundException extends RuntimeException {

    public DynamicEntityNotFoundException() {
        super("Dynamic Entity Not Found");
    }
}
