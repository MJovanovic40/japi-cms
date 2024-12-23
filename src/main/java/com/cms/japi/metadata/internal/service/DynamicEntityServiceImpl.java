package com.cms.japi.metadata.internal.service;

import com.cms.japi.logging.LogService;
import com.cms.japi.metadata.DynamicEntityService;
import com.cms.japi.metadata.internal.constants.DynamicEntityConstants;
import com.cms.japi.metadata.internal.entity.DynamicEntity;
import com.cms.japi.metadata.internal.exception.DynamicEntityNotFoundException;
import com.cms.japi.metadata.internal.repository.DynamicEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DynamicEntityServiceImpl implements DynamicEntityService {

    private final DynamicEntityRepository dynamicEntityRepository;

    @Override
    @LogService
    public DynamicEntity createDynamicEntity(String dynamicEntityName) {
        DynamicEntity newDynamicEntity = new DynamicEntity();
        newDynamicEntity.setName(dynamicEntityName);
        newDynamicEntity.setData(DynamicEntityConstants.EMPTY_DATA);
        return dynamicEntityRepository.save(newDynamicEntity);
    }

    @Override
    @LogService
    public List<DynamicEntity> getAll() {
        return dynamicEntityRepository.findAll();
    }

    @Override
    @LogService
    public DynamicEntity getDynamicEntity(Integer dynamicEntityId) {
        return dynamicEntityRepository.findById(dynamicEntityId)
                .orElseThrow(DynamicEntityNotFoundException::new);
    }

    @Override
    @LogService
    public void updateDynamicEntityData(Integer dynamicEntityId, String name, String dynamicEntityJsonString) {
        int rowsAffected = dynamicEntityRepository.updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString);
        if (rowsAffected < 1) throw new DynamicEntityNotFoundException();
    }

    @Override
    @LogService
    public void deleteDynamicEntity(Integer dynamicEntityId) {
        int rowsAffected = dynamicEntityRepository.deleteDynamicEntityById(dynamicEntityId);
        if (rowsAffected < 1) throw new DynamicEntityNotFoundException();
    }

}
