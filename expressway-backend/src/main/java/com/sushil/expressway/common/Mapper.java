package com.sushil.expressway.common;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;

import jakarta.persistence.EntityListeners;

@Service
@EntityListeners(AuditingEntityListener.class)
public class Mapper {

    public Consignment toConsignment(ConsignmentRequest request) {
        return Consignment.builder()
                .awbNUmber(request.getAwbNumber())
                .chennalPatner(request.getChannelPartner())
                .serviceType(request.getServiceType())
                .senderName(request.getSenderName())
                .senderContact(request.getSenderContact())
                .senderAddress(request.getSenderAddress())
                .receiverName(request.getReceiverName())
                .receiverAddress(request.getReceiverAddress())
                .weight(request.getWeight())
                .dimensions(request.getDimensions())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();
    }

}
