package com.cms.japi.classgeneration;

import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;

import java.util.List;

public interface GenerationService {

    List<Class<?>> generateEntities(List<DynamicClassProperties> entitiesProperties);
    void generateSupportingClasses(List<Class<?>> entities);

}
