package com.cms.japi.classgeneration.internal.generation.dto;

import com.cms.japi.classgeneration.internal.exceptions.ReflectionFieldException;

import java.lang.reflect.Field;

public class GeneratedDto {
    public void set(String key, Object value) {
        try {
            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(this, value);
            field.setAccessible(false);
        } catch (Exception e) {
            throw new ReflectionFieldException(e.getMessage());
        }
    }

    public Object get(String key) {
        try {
            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);
            Object value = field.get(this);
            field.setAccessible(false);
            return value;
        } catch (Exception e) {
            throw new ReflectionFieldException(e.getMessage());
        }
    }
}
