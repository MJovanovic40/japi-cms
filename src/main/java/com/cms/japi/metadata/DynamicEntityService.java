package com.cms.japi.metadata;

import java.util.List;

public interface DynamicEntityService {

    DynamicEntityDto createDynamicEntity(String dynamicEntityName);

    List<DynamicEntityDto> getAll();

    DynamicEntityDto getDynamicEntity(Integer dynamicEntityId);

    void updateDynamicEntityData(Integer dynamicEntityId, String name, String dynamicEntityJsonString);

    void deleteDynamicEntity(Integer dynamicEntityId);
}
