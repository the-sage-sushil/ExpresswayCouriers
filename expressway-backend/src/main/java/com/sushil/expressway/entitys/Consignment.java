package com.sushil.expressway.entitys;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consignments")
public class Consignment {

    @Id
    @GeneratedValue
    private Integer id;

    private String awbNUmber;
    private Integer bookedBy; // user id of the person who booked the consignment
    private String chennalPatner;
    private String serviceType; // e.g., Standard, Express , Surface , Express surface
    private String senderName; // Name of the person sending the consignment
    private String senderContact; // Contact number of the sender
    private String senderAddress;   // Address of the sender
    private String receiverName; // Name of the person receiving the consignment
    private String receiverAddress; // Address of the receiver
    private LocalDate bookingDate; // Date when the consignment was booked
    private String weight;  // Weight of the consignment
    private String dimensions;
    private Long price; // Expected or actual delivery date
    private String status;

}
