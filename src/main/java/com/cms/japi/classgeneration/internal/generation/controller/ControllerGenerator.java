package com.cms.japi.classgeneration.internal.generation.controller;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cms.japi.classgeneration.internal.utils.ClassGenerationUtils.createAnnotation;

@RequiredArgsConstructor
public class ControllerGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {
        return new ByteBuddy()
                .subclass(GeneratedController.class)
                .name(dynamicClassProperties.getName() + "Controller")
                .annotateType(AnnotationDescription.Builder.ofType(RestController.class).build())
                .annotateType(createAnnotation(RequestMapping.class, "/" + dynamicClassProperties.getName()))

                .defineMethod("create", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(GeneratedDto.class)
                .intercept(MethodDelegation.to(GeneratedController.class))
                .annotateMethod(createAnnotation(PostMapping.class, "/"))

                .defineMethod("getAll", ResponseEntity.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(GeneratedController.class))
                .annotateMethod(createAnnotation(GetMapping.class, "/"))

                .defineMethod("get", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class)
                .intercept(MethodDelegation.to(GeneratedController.class))
                .annotateMethod(createAnnotation(GetMapping.class, "/{id}"))
                .annotateMethod(createAnnotation(PathVariable.class, "id"))

                .defineMethod("update", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class, GeneratedDto.class)
                .intercept(MethodDelegation.to(GeneratedController.class))
                .annotateMethod(createAnnotation(PutMapping.class, "/{id}"))
                .annotateMethod(createAnnotation(PathVariable.class, "id"))

                .defineMethod("delete", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class)
                .intercept(MethodDelegation.to(GeneratedController.class))
                .annotateMethod(createAnnotation(DeleteMapping.class, "/{id}"))
                .annotateMethod(createAnnotation(PathVariable.class, "id"))

                .make()
                .load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
