package com.sushil.expressway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sushil.expressway.entitys.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
