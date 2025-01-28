package com.cms.japi.generation.events;

public class ScriptGeneratedEvent {
    private final String message;

    public ScriptGeneratedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
