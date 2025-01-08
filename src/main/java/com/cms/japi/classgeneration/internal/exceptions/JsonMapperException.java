package com.cms.japi.classgeneration.internal.exceptions;

public class JsonMapperException extends RuntimeException {
    public JsonMapperException(String message) {
        super("Json Parsing Failed: " + message);
    }
}
