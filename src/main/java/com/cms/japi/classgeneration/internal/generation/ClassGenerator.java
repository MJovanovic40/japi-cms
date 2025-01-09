package com.cms.japi.classgeneration.internal.generation;

import com.cms.japi.classgeneration.DynamicClassProperties;

public interface ClassGenerator {

    Class<?> generate(DynamicClassProperties dynamicClassProperties);
}
