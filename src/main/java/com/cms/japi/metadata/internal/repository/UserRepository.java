package com.cms.japi.metadata.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.japi.metadata.internal.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
