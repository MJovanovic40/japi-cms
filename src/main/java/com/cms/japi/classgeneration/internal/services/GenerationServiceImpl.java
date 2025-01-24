package com.cms.japi.classgeneration.internal.services;

import com.cms.japi.classgeneration.GenerationService;
import com.cms.japi.classgeneration.internal.generation.ClassGeneratorFactory;
import com.cms.japi.classgeneration.internal.generation.entity.GeneratedEntity;
import com.cms.japi.classgeneration.internal.generation.service.GeneratedService;
import com.cms.japi.classgeneration.internal.injection.BeanInjector;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {

    private final DynamicClassPropertiesService dynamicClassPropertiesService;
    private final BeanInjector beanInjector;

    @PostConstruct
    public void init() {
        generateDynamicClasses();
    }

    @Override
    public List<Class<?>> generateDynamicClasses() {
        // Get all entities
        // Generate Entity
        // Generate all Class Properties
        // Sort all class properties for the creation order
        // Generate classes

        List<DynamicClassProperties> entitiesProperties = dynamicClassPropertiesService.getClassPropertiesForAllEntities();

        entitiesProperties.forEach(properties -> {
            Class<?> generatedEntity = generateClass(properties);

            DynamicClassProperties repositoryProperties = generateRepositoryProperties(properties);
            Class<?> generatedRepository = generateClass(repositoryProperties);

//            System.out.println(beanInjector.injectBean(generatedRepository).getClass().getName());

            DynamicClassProperties exceptionHandlerProperties = generateExceptionHandlerProperties(properties);
            DynamicClassProperties dtoProperties = generateDtoProperties(properties);
        });


        return List.of();
    }

    private Class<?> generateClass(DynamicClassProperties properties) {
        return ClassGeneratorFactory.getClassGenerator(properties).generate();
    }

    private DynamicClassProperties generateExceptionHandlerProperties(DynamicClassProperties baseProperties) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "ExceptionHandler");
        properties.setType(DynamicClassType.EXCEPTION_HANDLER);
        return properties;
    }

    private DynamicClassProperties generateDtoProperties(DynamicClassProperties baseProperties) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Dto");
        properties.setType(DynamicClassType.DTO);
        properties.getFields().addAll(baseProperties.getFields());
        return properties;
    }

    private DynamicClassProperties generateRepositoryProperties(DynamicClassProperties baseProperties) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Repository");
        properties.setType(DynamicClassType.REPOSITORY);
        return properties;
    }

    private DynamicClassProperties generateServiceProperties(DynamicClassProperties baseProperties, JpaRepository<GeneratedEntity, Long> repository, Class<?> entity) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Service");
        properties.setType(DynamicClassType.SERVICE);
        properties.getDependencies().put(DynamicClassType.REPOSITORY, repository);
        properties.getDependencies().put(DynamicClassType.ENTITY, entity);
        return properties;
    }

    private DynamicClassProperties generateControllerProperties(DynamicClassProperties baseProperties, GeneratedService service) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Controller");
        properties.setType(DynamicClassType.CONTROLLER);
        properties.getDependencies().put(DynamicClassType.SERVICE, service);
        return properties;
    }

}
