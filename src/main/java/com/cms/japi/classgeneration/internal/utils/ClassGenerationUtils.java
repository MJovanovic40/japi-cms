package com.cms.japi.classgeneration.internal.utils;

import lombok.experimental.UtilityClass;
import net.bytebuddy.description.annotation.AnnotationDescription;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ClassGenerationUtils {

    public <T extends Annotation> AnnotationDescription createAnnotation(Class<T> annotationClass, String path) {
        return AnnotationDescription.Builder.ofType(annotationClass)
                .define("value", Arrays.toString(new String[]{path})) // Set the annotation value (path for mapping)
                .build();
    }

    public Object[] convertToVarArgs(Object obj, List<String> ignoredFields) {
        if (obj == null) throw new IllegalArgumentException("Object cannot be null");

        Field[] fields = obj.getClass().getDeclaredFields();

        List<Object> fieldValues = new ArrayList<>();

        for (Field field : fields) {
            if(ignoredFields.contains(field.getName())) continue;
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                fieldValues.add(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return fieldValues.toArray();
    }
}
