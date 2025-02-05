package com.cms.japi.metadata.internal.repositories.implementation;

import com.cms.japi.metadata.internal.entities.DynamicEntity;
import com.cms.japi.metadata.internal.exceptions.DynamicEntityExistsException;
import com.cms.japi.metadata.internal.repositories.DynamicEntityRepository;
import org.hibernate.type.SerializationException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class DynamicEntityRepositoryImpl implements DynamicEntityRepository {

    private List<DynamicEntity> dynamicEntityList = new ArrayList<>();

    private String fileLocation = "/home/milan/Desktop/serialized.japi";

    @Override
    public DynamicEntity save(DynamicEntity entity) {
        deserialize();
        if(dynamicEntityList.stream().anyMatch(e -> e.getId().equals(entity.getId()))) {
            throw new DynamicEntityExistsException();
        }
        entity.setId(getNextId());
        dynamicEntityList.add(entity);
        serialize();
        return entity;
    }

    @Override
    public List<DynamicEntity> findAll() {
        deserialize();
        return dynamicEntityList;
    }

    @Override
    public Optional<DynamicEntity> findById(Integer id) {
        deserialize();
        return dynamicEntityList.stream().filter(dynamicEntity -> dynamicEntity.getId().equals(id)).findFirst();
    }

    @Override
    public int updateDynamicEntityById(Integer id, String name, String data) {
        deserialize();
        DynamicEntity dynamicEntity = dynamicEntityList.stream().filter(entity -> entity.getId().equals(id)).findFirst().orElse(null);
        if (dynamicEntity == null) return 0;

        dynamicEntity.setName(name);
        dynamicEntity.setData(data);

        serialize();

        return 1;
    }

    @Override
    public int deleteDynamicEntityById(Integer id) {
        deserialize();
        DynamicEntity dynamicEntity = dynamicEntityList.stream().filter(entity -> entity.getId().equals(id)).findFirst().orElse(null);
        if (dynamicEntity == null) return 0;
        dynamicEntityList.remove(dynamicEntity);

        serialize();

        return 1;
    }

    private void serialize() {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileLocation, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(dynamicEntityList);
        } catch (IOException e) {
            throw new SerializationException("Unable to serialize dynamic entities", e);
        }
    }

    private void deserialize() {
        try(FileInputStream fileInputStream = new FileInputStream(fileLocation);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object deserialized = objectInputStream.readObject();

            if(deserialized == null) return;

            if(!(deserialized instanceof List<?>)) throw new SerializationException("Invalid object.", null);

            dynamicEntityList = (List<DynamicEntity>) deserialized;
        } catch (IOException e) {
            serialize();
            deserialize();
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Unable to deserialize dynamic entities", e);
        }
    }

    private Integer getNextId() {

        Optional<DynamicEntity> dynamicEntityOpt = dynamicEntityList.stream().max(new Comparator<DynamicEntity>() {
            @Override
            public int compare(DynamicEntity o1, DynamicEntity o2) {
                return o1.getId() - o2.getId();
            }
        });

        return dynamicEntityOpt.map(dynamicEntity -> dynamicEntity.getId() + 1).orElse(1);
    }
}
