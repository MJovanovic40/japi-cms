package com.cms.japi.classgeneration.internal.utility;

import com.cms.japi.classgeneration.internal.exceptions.JsonMapperException;
import com.cms.japi.classgeneration.DynamicClassProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper mapper;

    public DynamicClassProperties parseDynamicClassProperties(String jsonString) {
        try {
            return mapper.readValue(jsonString, DynamicClassProperties.class);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException(e.getMessage());
        }
    }
}
