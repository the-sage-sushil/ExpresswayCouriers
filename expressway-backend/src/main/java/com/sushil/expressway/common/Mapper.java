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
                .destPincode(request.getDestPincode())
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
                .contactPerson(request.getContactPerson())
                .address(request.getAddress())

                .airLocal250(request.getAirLocal250())
                .airLocal500(request.getAirLocal500())
                .airLocalAdd500(request.getAirLocalAdd500())
                .airNearby250(request.getAirNearby250())
                .airNearby500(request.getAirNearby500())
                .airNearbyAdd500(request.getAirNearbyAdd500())
                .airPanIndia250(request.getAirPanIndia250())
                .airPanIndia500(request.getAirPanIndia500())
                .airPanIndiaAdd500(request.getAirPanIndiaAdd500())
                .airSpecial250(request.getAirSpecial250())
                .airSpecial500(request.getAirSpecial500())
                .airSpecialAdd500(request.getAirSpecialAdd500())

                .premiumLocal500(request.getPremiumLocal500())
                .premiumLocalAdd500(request.getPremiumLocalAdd500())
                .premiumNearby500(request.getPremiumNearby500())
                .premiumNearbyAdd500(request.getPremiumNearbyAdd500())
                .premiumPanIndia500(request.getPremiumPanIndia500())
                .premiumPanIndiaAdd500(request.getPremiumPanIndiaAdd500())
                .premiumSpecial500(request.getPremiumSpecial500())
                .premiumSpecialAdd500(request.getPremiumSpecialAdd500())

                .surfaceLocal500(request.getSurfaceLocal500())
                .surfaceNearby500(request.getSurfaceNearby500())
                .surfacePanIndia500(request.getSurfacePanIndia500())
                .surfaceSpecial500(request.getSurfaceSpecial500())
                .build();
    }

    
}
