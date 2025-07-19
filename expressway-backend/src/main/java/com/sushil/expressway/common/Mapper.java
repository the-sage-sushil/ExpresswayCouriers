package com.sushil.expressway.common;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ClientRequest;
import com.sushil.expressway.models.ConsignmentRequest;

import jakarta.persistence.EntityListeners;

@Service
@EntityListeners(AuditingEntityListener.class)
public class Mapper {


    public Consignment toConsignment(ConsignmentRequest request) {
        return Consignment.builder()
                .trackingNumber(request.getTrackingNumber())
                .chennalPatner(request.getChannelPartner())
                .serviceType(request.getServiceType())
                .senderName(request.getSenderName())
                .senderContact(request.getSenderContact())
                .receiverName(request.getReceiverName())
                .receiverAddress(request.getReceiverAddress())
                .weight(request.getWeight())
                .dimensions(request.getDimensions())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus())
                .build();
    }

    public Client toClient(ClientRequest request) {
        return Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .contactNumber(request.getContactNumber())
                .address(request.getAddress())
                .standardPricePerKg(request.getStandardPricePerKg())
                .premiumPricePerKg(request.getPremiumPricePerKg())
                .surfacePricePerKg(request.getSurfacePricePerKg())
                .build();
    }

}
