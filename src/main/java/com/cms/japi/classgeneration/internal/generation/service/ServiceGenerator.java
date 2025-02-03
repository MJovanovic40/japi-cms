package com.cms.japi.classgeneration.internal.generation.service;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.classgeneration.internal.generation.entity.GeneratedEntity;
import com.cms.japi.classgeneration.internal.utils.ClassGenerationUtils;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class ServiceGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {

        Object repoObject = dynamicClassProperties.getDependencies().get(DynamicClassType.REPOSITORY);
        Object mapperObject = dynamicClassProperties.getDependencies().get(DynamicClassType.MAPPER);
        Object entityObject = dynamicClassProperties.getDependencies().get(DynamicClassType.ENTITY);

        DefaultServiceProvider serviceProvider = null;

        if(repoObject instanceof JpaRepository repository && mapperObject instanceof Mapper mapper && entityObject instanceof Class<?> entity) {
            serviceProvider = new DefaultServiceProvider(repository, mapper, (Class<? extends GeneratedEntity>) entity);
        } else {
            throw new RuntimeException("No service provider found");
        }

        return new ByteBuddy()
                .subclass(Object.class)
                .annotateType(ClassGenerationUtils.createAnnotation(Service.class, ""))
                .implement(GeneratedService.class)

                .defineMethod("create", GeneratedDto.class, Visibility.PUBLIC)
                .withParameters(Object[].class)
                .intercept(MethodDelegation.to(serviceProvider))

                .defineMethod("getAll", List.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(serviceProvider))

                .defineMethod("get", GeneratedDto.class, Visibility.PUBLIC)
                .withParameters(Long.class)
                .intercept(MethodDelegation.to(serviceProvider))

                .defineMethod("update", void.class, Visibility.PUBLIC)
                .withParameters(Object[].class)
                .intercept(MethodDelegation.to(serviceProvider))

                .defineMethod("delete", void.class, Visibility.PUBLIC)
                .withParameters(Long.class)
                .intercept(MethodDelegation.to(serviceProvider))

                .make()
                .load(JapiApplication.class.getClassLoader())
                .getLoaded();
    }
}
