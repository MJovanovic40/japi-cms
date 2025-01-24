package com.cms.japi.classgeneration.internal.generation;

import com.cms.japi.classgeneration.internal.generation.controller.ControllerGenerator;
import com.cms.japi.classgeneration.internal.generation.dto.DtoGenerator;
import com.cms.japi.classgeneration.internal.generation.entity.EntityGenerator;
import com.cms.japi.classgeneration.internal.generation.exceptions.ExceptionHandlerGenerator;
import com.cms.japi.classgeneration.internal.generation.repository.RepositoryGenerator;
import com.cms.japi.classgeneration.internal.generation.service.ServiceGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassGeneratorFactory {

    public ClassGenerator getClassGenerator(DynamicClassProperties properties) {
        switch (properties.getType()) {
            case DTO -> {
                return new DtoGenerator(properties);
            }
            case CONTROLLER -> {
                return new ControllerGenerator(properties);
            }
            case ENTITY -> {
                return new EntityGenerator(properties);
            }
            case EXCEPTION_HANDLER -> {
                return new ExceptionHandlerGenerator(properties);
            }
            case REPOSITORY -> {
                return new RepositoryGenerator(properties);
            } case SERVICE -> {
                return new ServiceGenerator(properties);
            } default -> throw new RuntimeException("Unsupported generator type: " + properties.getType());
        }
    }
}
