package com.sushil.expressway.entitys;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consignments")
@EntityListeners(AuditingEntityListener.class)
public class Consignment {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer bookedBy; // user id of the person who booked the consignment

    @Column(unique = true)
    private String awbNUmber;

    private String chennalPatner;
    private String serviceType; // e.g., Standard, Express , Surface , Express surface
    private String senderName; // Name of the person sending the consignment
    private String senderContact; // Contact number of the sender

    private String senderAddress; // Address of the sender
    private String receiverName; // Name of the person receiving the consignment
    private String receiverAddress; // Address of the receiver

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate bookingDate; // Date when the consignment was booked

    private Integer weight; // Weight of the consignment

    private String dimensions;

    private Long price; // Expected or actual delivery date
    private String status;

}
