package com.cms.japi.config.internal.mapper;

import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DynamicMapperConfig {

    protected final DynamicClassPropertiesService dynamicClassPropertiesService;

    @Bean("dynamicMapper")
    public Mapper mapper() {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(new GeneratedClassMapperBuilder(dynamicClassPropertiesService.getClassPropertiesForAllEntities()))
                .build();
    }
}