package com.cms.japi.classgeneration.internal.injection;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DynamicRepositoryInitializer {

    private final EntityManager entityManager;

     public JpaRepository initializeRepository(Class<? extends JpaRepository<?, Long>> repository) {
         RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
         JpaRepository<?, Long> repositoryInstance = factory.getRepository(repository);
         return repositoryInstance;
     }
}
