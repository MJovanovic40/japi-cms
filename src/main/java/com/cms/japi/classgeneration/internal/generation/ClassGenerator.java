package com.cms.japi.classgeneration.internal.generation;

import com.cms.japi.classgeneration.internal.properties.DynamicClassProperties;

public interface ClassGenerator {

    Class<?> generate(DynamicClassProperties dynamicClassProperties);
}
