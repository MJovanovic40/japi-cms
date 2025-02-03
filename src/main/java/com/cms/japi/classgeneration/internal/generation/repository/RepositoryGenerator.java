package com.cms.japi.classgeneration.internal.generation.repository;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class RepositoryGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {
        Class<?> entityClass = (Class<?>) dynamicClassProperties.getDependencies().get(DynamicClassType.ENTITY);

        TypeDescription.Generic entityType = TypeDescription.Generic.Builder.parameterizedType(
                JpaRepository.class, entityClass, Long.class).build();

        return new ByteBuddy()
                .makeInterface(entityType)
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
