package com.sushil.expressway.entitys;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@EntityListeners(AuditingEntityListener.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String contactPerson;
    private String contactNumber;
    private String address;

    
    private Double airLocal250;
    private Double airLocal500;
    private Double airLocalAdd500;

    private Double airNearby250;
    private Double airNearby500;
    private Double airNearbyAdd500;

    private Double airPanIndia250;
    private Double airPanIndia500;
    private Double airPanIndiaAdd500;

    private Double airSpecial250;
    private Double airSpecial500;
    private Double airSpecialAdd500;
    // private Double airEcommarce;

    private Double surfaceLocal500;
    private Double surfaceNearby500;
    private Double surfacePanIndia500;
    private Double surfaceSpecial500;
    // private Double surfaceEcommarce;

    private Double premiumLocal500;
    private Double premiumLocalAdd500;
    private Double premiumNearby500;
    private Double premiumNearbyAdd500;
    private Double premiumPanIndia500;
    private Double premiumPanIndiaAdd500;
    private Double premiumSpecial500;
    private Double premiumSpecialAdd500;

}
