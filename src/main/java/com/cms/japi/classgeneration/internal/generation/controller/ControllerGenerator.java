package com.cms.japi.classgeneration.internal.generation.controller;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.classgeneration.internal.generation.service.GeneratedService;
import com.cms.japi.classgeneration.internal.utils.ClassGenerationUtils;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public class ControllerGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {

        Object serviceObj = dynamicClassProperties.getDependencies().get(DynamicClassType.SERVICE);

        if(!(serviceObj instanceof GeneratedService)) throw new RuntimeException("Invalid dependency.");

        DefaultControllerProvider defaultControllerProvider = new DefaultControllerProvider((GeneratedService) serviceObj);

        return new ByteBuddy()
                .subclass(DefaultControllerProvider.class)
                .name(dynamicClassProperties.getName())
                .annotateType(AnnotationDescription.Builder.ofType(RestController.class).build())
                .annotateType(ClassGenerationUtils.createAnnotation(RequestMapping.class, "/" + dynamicClassProperties.getName()))

                .defineMethod("create", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(GeneratedDto.class)
                .intercept(MethodDelegation.to(defaultControllerProvider))
                .annotateMethod(ClassGenerationUtils.createAnnotation(PostMapping.class, "/"))

                .defineMethod("getAll", ResponseEntity.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(defaultControllerProvider))
                .annotateMethod(ClassGenerationUtils.createAnnotation(GetMapping.class, "/"))

                .defineMethod("get", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class)
                .intercept(MethodDelegation.to(defaultControllerProvider))
                .annotateMethod(ClassGenerationUtils.createAnnotation(GetMapping.class, "/{id}"))
                .annotateMethod(ClassGenerationUtils.createAnnotation(PathVariable.class, "id"))

                .defineMethod("update", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class, GeneratedDto.class)
                .intercept(MethodDelegation.to(defaultControllerProvider))
                .annotateMethod(ClassGenerationUtils.createAnnotation(PutMapping.class, "/{id}"))
                .annotateMethod(ClassGenerationUtils.createAnnotation(PathVariable.class, "id"))

                .defineMethod("delete", ResponseEntity.class, Visibility.PUBLIC)
                .withParameters(Integer.class)
                .intercept(MethodDelegation.to(defaultControllerProvider))
                .annotateMethod(ClassGenerationUtils.createAnnotation(DeleteMapping.class, "/{id}"))
                .annotateMethod(ClassGenerationUtils.createAnnotation(PathVariable.class, "id"))

                .make()
                .load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
