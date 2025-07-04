package com.sushil.expressway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;


public interface ConsignmentRepository extends JpaRepository<Consignment,Integer> {

    void save(ConsignmentRequest request);



}
