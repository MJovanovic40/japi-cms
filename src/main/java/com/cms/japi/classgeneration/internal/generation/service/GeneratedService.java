package com.cms.japi.classgeneration.internal.generation.service;

import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;

import java.util.List;

public interface GeneratedService {

    GeneratedDto create(Object... values);
    List<GeneratedDto> getAll();
    GeneratedDto get(Long id);
    void update(Object... values);
    void delete(Long dynamicEntityId);
}
