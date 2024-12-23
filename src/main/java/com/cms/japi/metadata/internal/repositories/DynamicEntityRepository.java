package com.cms.japi.metadata.internal.repositories;

import com.cms.japi.metadata.internal.entities.DynamicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DynamicEntityRepository extends JpaRepository<DynamicEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update DynamicEntity d set d.name = ?2, d.data = ?3 where d.id = ?1")
    int updateDynamicEntityById(Integer id, String name, String data);

    @Transactional
    @Modifying
    @Query("delete from DynamicEntity d where d.id = ?1")
    int deleteDynamicEntityById(Integer id);

}
