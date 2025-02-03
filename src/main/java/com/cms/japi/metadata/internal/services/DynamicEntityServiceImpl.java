package com.cms.japi.metadata.internal.services;

import com.cms.japi.logging.LogService;
import com.cms.japi.metadata.DynamicEntityService;
import com.cms.japi.metadata.internal.constants.DynamicEntityConstants;
import com.cms.japi.metadata.DynamicEntityDto;
import com.cms.japi.metadata.internal.entities.DynamicEntity;
import com.cms.japi.metadata.internal.exceptions.DynamicEntityNotFoundException;
import com.cms.japi.metadata.internal.repositories.DynamicEntityRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@LogService
@RequiredArgsConstructor
public class DynamicEntityServiceImpl implements DynamicEntityService {

    private final DynamicEntityRepository dynamicEntityRepository;
    private final Mapper mapper;

    @Override
    public DynamicEntityDto createDynamicEntity(String dynamicEntityName) {
        DynamicEntity newDynamicEntity = new DynamicEntity();
        newDynamicEntity.setName(dynamicEntityName);
        newDynamicEntity.setData(DynamicEntityConstants.EMPTY_DATA);
        return mapper.map(dynamicEntityRepository.save(newDynamicEntity), DynamicEntityDto.class);
    }

    @Override
    public List<DynamicEntityDto> getAll() {
        return dynamicEntityRepository.findAll()
                .stream()
                .map(dynamicEntity -> mapper.map(dynamicEntity, DynamicEntityDto.class))
                .toList();
    }

    @Override
    public DynamicEntityDto getDynamicEntity(Integer dynamicEntityId) {
        return mapper.map(dynamicEntityRepository.findById(dynamicEntityId)
                .orElseThrow(DynamicEntityNotFoundException::new), DynamicEntityDto.class);
    }

    @Override
    public void updateDynamicEntityData(Integer dynamicEntityId, String name, String dynamicEntityJsonString) {
        int rowsAffected = dynamicEntityRepository.updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString);
        if (rowsAffected < 1) throw new DynamicEntityNotFoundException();
    }

    @Override
    public void deleteDynamicEntity(Integer dynamicEntityId) {
        int rowsAffected = dynamicEntityRepository.deleteDynamicEntityById(dynamicEntityId);
        if (rowsAffected < 1) throw new DynamicEntityNotFoundException();
    }

}
