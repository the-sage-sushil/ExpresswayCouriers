package com.sushil.expressway.models;

import java.time.LocalDate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;


public class ConsignmentRequest {
    

    @Nonnull
    @Column(unique = true)
    private String awbNumber;

    @Nonnull
    private String channelPartner;
    @Nonnull
    private String serviceType; // e.g., Standard, Express , Surface , Express surface
    @Nonnull
    private String senderName; // Name of the person sending the consignment
    @Nonnull
    private String senderContact; // Contact number of the sender

    private String senderAddress;   // Address of the sender
    @Nonnull
    private String receiverName; // Name of the person receiving the consignment
    @Nonnull
    private String receiverAddress; // Address of the receiver
    
    @Nonnull
    private Integer weight;  // Weight of the consignment
    
    private String dimensions;
    
    @Nonnull
    private Long price; // Expected or actual delivery date
    
    private String status;

    public ConsignmentRequest() {}

    public ConsignmentRequest(String awbNumber, String channelPartner, String serviceType, String senderName, String senderContact, String senderAddress, String receiverName, String receiverAddress, LocalDate bookingDate, Integer weight, String dimensions, Long price, String status) {
        this.awbNumber = awbNumber;
        this.channelPartner = channelPartner;
        this.serviceType = serviceType;
        this.senderName = senderName;
        this.senderContact = senderContact;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.weight = weight;
        this.dimensions = dimensions;
        this.price = price;
        this.status = status;
    }

    public String getAwbNumber() { return awbNumber; }
    public void setAwbNumber(String awbNumber) { this.awbNumber = awbNumber; }

    public String getChannelPartner() { return channelPartner; }
    public void setChannelPartner(String channelPartner) { this.channelPartner = channelPartner; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderContact() { return senderContact; }
    public void setSenderContact(String senderContact) { this.senderContact = senderContact; }

    public String getSenderAddress() { return senderAddress; }
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
