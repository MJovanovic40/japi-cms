package com.cms.japi.classgeneration.internal.utils;

import lombok.experimental.UtilityClass;
import net.bytebuddy.description.annotation.AnnotationDescription;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@UtilityClass
public class ClassGenerationUtils {

    // Utility method to create annotations dynamically
    public <T extends Annotation> AnnotationDescription createAnnotation(Class<T> annotationClass, String path) {
        return AnnotationDescription.Builder.ofType(annotationClass)
                .define("value", Arrays.toString(new String[]{path})) // Set the annotation value (path for mapping)
                .build();
    }
}
