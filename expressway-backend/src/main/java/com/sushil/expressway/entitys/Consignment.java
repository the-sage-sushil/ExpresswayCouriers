package com.sushil.expressway.entitys;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate bookingDate; // Date when the consignment was booked
    
    private Integer bookedBy; // user id of the person who booked the consignment

    @Column(unique = true)
    private String trackingNumber;
    private String chennalPatner;
    
    private String serviceType; // e.g., Standard, Express , Surface , Express surface
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id")
    private Client client; // Nullable. if null => walk-in
    
    private String senderName; // Name of the person sending the consignment
    private String senderContact; // Contact number of the sender
    
    private String receiverName; // Name of the person receiving the consignment
    private String receiverAddress; // Address of the receiver
    private String receiverContact; // Contact number of the sender


    private Integer weight; // Weight of the consignment
    private String dimensions;
    private Integer numberOfPackages;

    private String paymentMode;
    private Long totalAmount; // Expected or actual delivery date
    
    private LocalDate expectedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private String status;

}
