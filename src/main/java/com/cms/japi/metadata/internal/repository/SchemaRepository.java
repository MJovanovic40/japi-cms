package com.cms.japi.metadata.internal.repository;

import com.cms.japi.metadata.internal.entity.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaRepository extends JpaRepository<Schema, Integer> {

}
