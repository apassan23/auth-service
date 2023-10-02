package com.phoenix.authservice.repository;

import com.phoenix.authservice.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
