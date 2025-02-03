package com.cms.japi.classgeneration;

import java.util.List;

public interface GenerationService {

    List<Class<?>> generateEntities();
    void generateSupportingClasses(List<Class<?>> entities);

}
