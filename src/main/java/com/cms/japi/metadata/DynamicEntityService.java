package com.cms.japi.metadata;

import com.cms.japi.metadata.internal.entity.DynamicEntity;

import java.util.List;

public interface DynamicEntityService {

    DynamicEntity createDynamicEntity(String dynamicEntityName);

    List<DynamicEntity> getAll();

    DynamicEntity getDynamicEntity(Integer dynamicEntityId);

    void updateDynamicEntityData(Integer dynamicEntityId, String name, String dynamicEntityJsonString);

    void deleteDynamicEntity(Integer dynamicEntityId);
}
