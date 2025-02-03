package com.cms.japi.classgeneration.internal.injection;

import com.cms.japi.classgeneration.GenerationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EntityInjector {

    private final DataSource dataSource;
    private final GenerationService generationService;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // Create a custom ServiceRegistry and apply Hibernate settings
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.connection.datasource", dataSource)
                .build();

        // Register the dynamic entity
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        List<Class<?>> generatedEntities = generationService.generateEntities();

        generatedEntities.forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.buildMetadata();

        SessionFactory sessionFactory = metadata.buildSessionFactory();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("com.example.demo"); // Static entities
        factory.setJpaProperties(getJpaProperties());

        // Inject the custom SessionFactory
        factory.getJpaPropertyMap().put("hibernate.session_factory", sessionFactory);

        generationService.generateSupportingClasses(generatedEntities);

        return factory;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

}
