package com.cms.japi.classgeneration.internal.services;

import com.cms.japi.classgeneration.GenerationService;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {

    private final DynamicClassPropertiesService dynamicEntityService;

    @Override
    public List<Class<?>> generateDynamicClasses() {
        return List.of();
    }
}
