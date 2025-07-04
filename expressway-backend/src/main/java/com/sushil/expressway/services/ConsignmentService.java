package com.sushil.expressway.services;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import com.sushil.expressway.common.Mapper;
import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;
import com.sushil.expressway.repositories.ConsignmentRepository;

import jakarta.persistence.EntityListeners;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ConsignmentService {

    private final Mapper mapper;
    private final ConsignmentRepository consignmentRepository;

    public Integer save(ConsignmentRequest request) {

        Consignment consignment = mapper.toConsignment(request);
        return consignmentRepository.save(consignment).getId();
    }

    public Object getBookings() {
         return consignmentRepository.findAll();
    }

}
