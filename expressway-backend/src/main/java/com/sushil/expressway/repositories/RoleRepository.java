package com.sushil.expressway.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sushil.expressway.entitys.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

}
