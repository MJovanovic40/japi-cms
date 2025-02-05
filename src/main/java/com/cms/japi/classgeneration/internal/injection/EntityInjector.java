package com.cms.japi.classgeneration.internal.injection;

import com.cms.japi.classgeneration.GenerationService;
import com.cms.japi.classgeneration.internal.generation.entity.GeneratedEntity;
import com.cms.japi.classgeneration.internal.generation.service.GeneratedService;
import com.cms.japi.classgeneration.internal.services.GenerationServiceImpl;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.cms.japi.metadata.internal.entities.DynamicEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EntityInjector {

    private final DataSource dataSource;
//    private final EntityManager entityManager;
    private final GenerationServiceImpl generationService;
    private final DynamicClassPropertiesService dynamicClassPropertiesService;
    private final BeanInjector beanInjector;
    private List<Class<?>> generatedEntities;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        List<DynamicClassProperties> entitiesProperties = dynamicClassPropertiesService.getClassPropertiesForAllEntities();

        //Create a custom ServiceRegistry and apply Hibernate settings
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.connection.datasource", dataSource)
                .build();

        // Register the dynamic entity
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        generatedEntities = generationService.generateEntities(entitiesProperties);

        generatedEntities.forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.buildMetadata();

        SessionFactory sessionFactory = metadata.buildSessionFactory();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("com.japi.cms"); // Static entities
        factory.setJpaProperties(getJpaProperties());

        //Inject the custom SessionFactory
        factory.getJpaPropertyMap().put("hibernate.session_factory", sessionFactory);

        this.generateSupportingClasses(entitiesProperties);

        return factory;
    }

    private void generateSupportingClasses(List<DynamicClassProperties> entitiesProperties) {
        entitiesProperties.forEach(properties -> {
//            DynamicClassProperties exceptionHandlerProperties = generateExceptionHandlerProperties(properties);
//            Class<?> generatedExceptionHandler = generateClass(exceptionHandlerProperties);

            DynamicClassProperties dtoProperties = generationService.generateDtoProperties(properties);
            Class<?> generatedDto = generationService.generateClass(dtoProperties);

//            DynamicClassProperties repositoryProperties = generationService.generateRepositoryProperties(properties, generatedEntities.get(entitiesProperties.indexOf(properties)));
//            Class<? extends JpaRepository<? extends GeneratedEntity, Long>> generatedRepository = (Class<? extends JpaRepository<? extends GeneratedEntity, Long>>) generationService.generateClass(repositoryProperties);
//            JpaRepository repository = this.initializeRepository(generatedRepository);
//
//            DynamicClassProperties serviceProperties = generationService.generateServiceProperties(properties, repository, generatedEntities.get(entitiesProperties.indexOf(properties)));
//            Class<?> generatedService = generationService.generateClass(serviceProperties);
//            beanInjector.injectBean(generatedService);
//
//            GeneratedService service = (GeneratedService) beanInjector.getBean(generatedService);
//
//            DynamicClassProperties controllerProperties = generationService.generateControllerProperties(properties, service);
//            Class<?> generatedController = generationService.generateClass(controllerProperties);
//
//            beanInjector.injectBean(generatedController);
        });
    }

//    private JpaRepository initializeRepository(Class<? extends JpaRepository<?, Long>> repository) {
//        RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
//        JpaRepository<?, Long> repositoryInstance = factory.getRepository(repository);
//        beanInjector.injectBean(repository);
//        return repositoryInstance;
//    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

}
