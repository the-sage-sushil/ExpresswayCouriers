package com.sushil.expressway.models;

import com.sushil.expressway.entitys.Client;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsignmentRequest {

    @Nonnull
    @Column(unique = true)
    private String trackingNumber;

    @Nonnull
    private String channelPartner;
    @Nonnull
    private String serviceType; // e.g., Standard, Express , Surface , Express surface
    @Nonnull
    private String senderName; // Name of the person sending the consignment
    @Nonnull
    private String senderContact; // Contact number of the sender

    private String senderAddress; // Address of the sender
    @Nonnull
    private Integer destPincode; // Destination Pincode
    @Nonnull
    private String receiverName; // Name of the person receiving the consignment
    @Nonnull
    private String receiverAddress; // Address of the receiver

    @Nonnull
    private Integer weight; // Weight of the consignment

    private String dimensions;


    private Client client; // Nullable. if null => walk-in

    @Nonnull
    private Long totalAmount; 
    private String status;

}
