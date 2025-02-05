package com.cms.japi.classgeneration.internal.services;

import com.cms.japi.classgeneration.GenerationService;
import com.cms.japi.classgeneration.internal.generation.ClassGeneratorFactory;
import com.cms.japi.classgeneration.internal.generation.entity.GeneratedEntity;
import com.cms.japi.classgeneration.internal.generation.service.GeneratedService;
import com.cms.japi.classgeneration.internal.injection.BeanInjector;
import com.cms.japi.classgeneration.internal.injection.DynamicRepositoryInitializer;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {

//    private final DynamicClassPropertiesService dynamicClassPropertiesService;
//    private final BeanInjector beanInjector;
//    private final DynamicRepositoryInitializer dynamicRepositoryInitializer;

    @Override
    public List<Class<?>> generateEntities(List<DynamicClassProperties> entitiesProperties) {
        List<Class<?>> generatedEntities = new ArrayList<>();
        entitiesProperties.forEach(properties -> {
            Class<?> generatedEntity = generateClass(properties);
            generatedEntities.add(generatedEntity);
        });
        return generatedEntities;
    }

    @Override
    public void generateSupportingClasses(List<Class<?>> entities) {

//        List<DynamicClassProperties> entitiesProperties = dynamicClassPropertiesService.getClassPropertiesForAllEntities();
//
//        entitiesProperties.forEach(properties -> {
////            DynamicClassProperties exceptionHandlerProperties = generateExceptionHandlerProperties(properties);
////            Class<?> generatedExceptionHandler = generateClass(exceptionHandlerProperties);
//
//            DynamicClassProperties dtoProperties = generateDtoProperties(properties);
//            Class<?> generatedDto = generateClass(dtoProperties);
//
//            DynamicClassProperties repositoryProperties = generateRepositoryProperties(properties, entities.get(entitiesProperties.indexOf(properties)));
//            Class<? extends JpaRepository<? extends GeneratedEntity, Long>> generatedRepository = (Class<? extends JpaRepository<? extends GeneratedEntity, Long>>) generateClass(repositoryProperties);
//            JpaRepository repository = this.initializeRepository(generatedRepository);
//
//            DynamicClassProperties serviceProperties = generateServiceProperties(properties, repository, entities.get(entitiesProperties.indexOf(properties)));
//            Class<?> generatedService = generateClass(serviceProperties);
//            beanInjector.injectBean(generatedService);
//
//            GeneratedService service = (GeneratedService) beanInjector.getBean(generatedService);
//
//            DynamicClassProperties controllerProperties = generateControllerProperties(properties, service);
//            Class<?> generatedController = generateClass(controllerProperties);
//
//            beanInjector.injectBean(generatedController);
//        });
    }


    public Class<?> generateClass(DynamicClassProperties properties) {
        return ClassGeneratorFactory.getClassGenerator(properties).generate();
    }

    public DynamicClassProperties generateExceptionHandlerProperties(DynamicClassProperties baseProperties) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "ExceptionHandler");
        properties.setType(DynamicClassType.EXCEPTION_HANDLER);
        return properties;
    }

    public DynamicClassProperties generateDtoProperties(DynamicClassProperties baseProperties) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Dto");
        properties.setType(DynamicClassType.DTO);
        properties.getFields().addAll(baseProperties.getFields());
        return properties;
    }

    public DynamicClassProperties generateRepositoryProperties(DynamicClassProperties baseProperties, Class<?> entity) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Repository");
        properties.setType(DynamicClassType.REPOSITORY);
        properties.getDependencies().put(DynamicClassType.ENTITY, entity);

        return properties;
    }

    public DynamicClassProperties generateServiceProperties(DynamicClassProperties baseProperties, JpaRepository<GeneratedEntity, Long> repository, Class<?> entity) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Service");
        properties.setType(DynamicClassType.SERVICE);
        properties.getDependencies().put(DynamicClassType.REPOSITORY, repository);
        properties.getDependencies().put(DynamicClassType.ENTITY, entity);
        return properties;
    }

    public DynamicClassProperties generateControllerProperties(DynamicClassProperties baseProperties, GeneratedService service) {
        DynamicClassProperties properties = new DynamicClassProperties();
        properties.setName(baseProperties.getName() + "Controller");
        properties.setType(DynamicClassType.CONTROLLER);
        properties.getDependencies().put(DynamicClassType.SERVICE, service);
        return properties;
    }


}
