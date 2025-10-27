package com.sushil.expressway.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TatRequest {
    private String pickupPincode;
    private String deliveryPincode;
    private double weight;
    private String courierType;
    private boolean isQRBooking;
    private double length;
    private double breadth;
    private double height;
    private String commodityId;
    private String commodityName;
    private double declaredPrice;
    private String commodityCode;
}

