package com.sushil.expressway.services;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final ClientService clientService;

    public Long save(ConsignmentRequest request) {

        
        Consignment consignment = mapper.toConsignment(request);
        if(request.getClient() != null) {
            consignment.setClient(clientService.getClientById(request.getClient()));
        }
        return consignmentRepository.save(consignment).getId();
    }

    public Object getBookings() {
        return consignmentRepository.findAll();
    }

    public List<Consignment> getConsignmentByClientId(Long clientId) {
        
        return consignmentRepository.findAllConsignmentByClientId(clientId);
    }

}
