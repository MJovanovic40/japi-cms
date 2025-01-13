package com.cms.japi.commons.dynamicclassproperties.internal;

import com.cms.japi.classgeneration.internal.exceptions.JsonMapperException;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.cms.japi.metadata.DynamicEntityDto;
import com.cms.japi.metadata.DynamicEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicClassPropertiesServiceImpl implements DynamicClassPropertiesService {

    private final ObjectMapper mapper;
    private final DynamicEntityService dynamicEntityService;

    @Override
    public List<DynamicClassProperties> getAllClassProperties() {
        return dynamicEntityService.getAll().stream()
                .map(entity -> parseDynamicClassProperties(entity.getData()))
                .toList();
    }

    private DynamicClassProperties parseDynamicClassProperties(String jsonString) {
        try {
            return mapper.readValue(jsonString, DynamicClassProperties.class);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException(e.getMessage());
        }
    }
}
