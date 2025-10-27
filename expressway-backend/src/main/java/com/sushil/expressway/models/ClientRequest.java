package com.sushil.expressway.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ClientRequest {

    @NotEmpty
    private String name;
    private String address;

    @NotEmpty
    private String email;

    private String contactPerson;

    @NotEmpty
    private String contactNumber;

    
    
    @NotNull(message = "airLocal250 price per kg is required")
    private Double airLocal250;
    @NotNull(message = "airLocal500 price per kg is required")
    private Double airLocal500;
    @NotNull(message = "airLocalAdd500 price per kg is required")
    private Double airLocalAdd500;
    @NotNull(message = "airNearby250 price per kg is required")
    private Double airNearby250;
    @NotNull(message = "airNearby500 price per kg is required")
    private Double airNearby500;
    @NotNull(message = "airNearbyAdd500 price per kg is required")
    private Double airNearbyAdd500;
    @NotNull(message = "airPanIndia250 price per kg is required")
    private Double airPanIndia250;
    @NotNull(message = "airPanIndia500 price per kg is required")
    private Double airPanIndia500;
    @NotNull(message = "airPanIndiaAdd500 price per kg is required")
    private Double airPanIndiaAdd500;
    @NotNull(message = "airSpecial250 price per kg is required")
    private Double airSpecial250;
    @NotNull(message = "airSpecial500 price per kg is required")
    private Double airSpecial500;
    @NotNull(message = "airSpecialAdd500 price per kg is required")
    private Double airSpecialAdd500;
    // @NotNull(message = "airEcommarce price per kg is required")
    // private Double airEcommarce;
    
    @NotNull(message = "surfaceLocal price per kg is required")
    private Double surfaceLocal500;
    @NotNull(message = "surfaceNearby price per kg is required")
    private Double surfaceNearby500;
    @NotNull(message = "surfacePanIndia price per kg is required")
    private Double surfacePanIndia500;
    @NotNull(message = "surfaceSpecial price per kg is required")
    private Double surfaceSpecial500;
    // @NotNull(message = "surfaceEcommarce price per kg is required")
    // private Double surfaceEcommarce;
    
    @NotNull(message = "premiumLocal500 price per kg is required")
    private Double premiumLocal500;
    @NotNull(message = "premiumLocalAdd500 price per kg is required")
    private Double premiumLocalAdd500;
    @NotNull(message = "premiumNearby500 price per kg is required")
    private Double premiumNearby500;
    @NotNull(message = "premiumNearbyAdd500 price per kg is required")
    private Double premiumNearbyAdd500;
    @NotNull(message = "premiumPanIndia500 price per kg is required")
    private Double premiumPanIndia500;
    @NotNull(message = "premiumPanIndiaAdd500 price per kg is required")
    private Double premiumPanIndiaAdd500;
    @NotNull(message = "premiumSpecial500 price per kg is required")
    private Double premiumSpecial500;
    @NotNull(message = "premiumSpecialAdd500 price per kg is required")
    private Double premiumSpecialAdd500;
}

