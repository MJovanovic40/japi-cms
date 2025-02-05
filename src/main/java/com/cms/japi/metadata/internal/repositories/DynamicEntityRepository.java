package com.cms.japi.metadata.internal.repositories;

import com.cms.japi.metadata.internal.entities.DynamicEntity;

import java.util.List;
import java.util.Optional;

public interface DynamicEntityRepository {

    DynamicEntity save(DynamicEntity entity);
    List<DynamicEntity> findAll();
    Optional<DynamicEntity> findById(Integer id);
    int updateDynamicEntityById(Integer id, String name, String data);
    int deleteDynamicEntityById(Integer id);

}
