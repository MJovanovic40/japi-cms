package com.cms.japi.config.internal.mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultMapperConfig {

    @Bean("defaultMapper")
    public Mapper getMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}