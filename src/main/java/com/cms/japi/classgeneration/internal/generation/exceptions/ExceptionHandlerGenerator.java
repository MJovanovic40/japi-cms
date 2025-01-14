package com.cms.japi.classgeneration.internal.generation.exceptions;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerGenerator implements ClassGenerator {

    @Override
    public Class<?> generate(DynamicClassProperties dynamicClassProperties) {
        return new ByteBuddy()
                .subclass(Object.class)
                .name(dynamicClassProperties.getName())
                .annotateType(AnnotationDescription.Builder.ofType(ControllerAdvice.class).build())
                .defineMethod("handleDynamicException", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(DynamicException.class)
                .intercept(MethodDelegation.to(new DefaultExceptionHandlerProvider()))
                .annotateType(
                        AnnotationDescription.Builder.ofType(ExceptionHandler.class)
                                .define("value", DynamicException.class)
                                .build()
                )
                .make()
                .load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
