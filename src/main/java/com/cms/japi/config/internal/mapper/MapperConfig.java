package com.cms.japi.config.internal.mapper;

import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    protected final DynamicClassPropertiesService dynamicClassPropertiesService;

    @Bean
    public Mapper mapper() {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(new GeneratedClassMapperBuilder(dynamicClassPropertiesService.getAllClassProperties()))
                .build();
    }

}

