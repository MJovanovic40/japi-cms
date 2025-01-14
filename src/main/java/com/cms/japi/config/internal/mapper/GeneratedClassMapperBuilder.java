package com.cms.japi.config.internal.mapper;

import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.classgeneration.internal.generation.entity.GeneratedEntity;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassType;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GeneratedClassMapperBuilder extends BeanMappingBuilder {

    private final List<DynamicClassProperties> dynamicClassProperties;

    @Override
    protected void configure() {
        dynamicClassProperties.forEach(properties -> {
            if(!DynamicClassType.ENTITY.equals(properties.getType())) return;

            properties.getFields().forEach(field ->
                mapping(GeneratedEntity.class, GeneratedDto.class)
                        .fields(
                                properties.getName() + "_" + field.getName(),
                                field.getName()
                        )
            );
        });
    }
}
