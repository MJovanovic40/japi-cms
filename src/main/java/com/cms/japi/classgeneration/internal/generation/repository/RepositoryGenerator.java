package com.cms.japi.classgeneration.internal.generation.repository;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class RepositoryGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {
        return new ByteBuddy()
                .makeInterface()
                .name(dynamicClassProperties.getName())
                .annotateType(AnnotationDescription.Builder.ofType(Repository.class).build())
                .defineMethod("deleteById", void.class, Visibility.PUBLIC)
                .withParameters(Long.class)
                .withoutCode()
                .make()
                .load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
