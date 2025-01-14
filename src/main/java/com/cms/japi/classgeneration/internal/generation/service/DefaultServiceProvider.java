package com.cms.japi.classgeneration.internal.generation.service;

import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.classgeneration.internal.generation.exceptions.DynamicException;
import com.cms.japi.classgeneration.internal.generation.model.GeneratedEntity;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiredArgsConstructor
public class DefaultServiceProvider {

    private final JpaRepository<GeneratedEntity, Long> repository;
    private final Mapper mapper;
    private final Class<? extends GeneratedEntity> entityClass;

    public GeneratedDto create(Object... values) {
        Field[] fields = entityClass.getDeclaredFields();

        if(values.length != fields.length) throw new DynamicException("Fields mismatch");

        GeneratedEntity entity = null;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        setValues(entity, values);

        repository.save(entity);

        return mapper.map(repository.save(entity), GeneratedDto.class);
    }

    public List<GeneratedDto> getAll() {
        return repository.findAll()
                .stream()
                .map(dynamicEntity -> mapper.map(dynamicEntity, GeneratedDto.class))
                .toList();
    }

    public GeneratedDto get(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new DynamicException("Entity not found by id.")), GeneratedDto.class);
    }

    public void update(Object... values) {
        Field[] fields = entityClass.getDeclaredFields();

        if(values.length != fields.length) throw new DynamicException("Fields mismatch");

        GeneratedEntity entity = repository.findById((Long)values[0]).orElseThrow(() -> new DynamicException("Entity not found by id."));

        setValues(entity, values);

        repository.save(entity);
    }

    public void delete(Long dynamicEntityId) {
        repository.deleteById(dynamicEntityId);
    }

    private void setValues(GeneratedEntity entity, Object... values) {
        Field[] fields = entityClass.getDeclaredFields();

        for(int i = 0; i <  fields.length; i++) {
            entity.set(fields[i].getName(), values[i]);
        }
    }
}
