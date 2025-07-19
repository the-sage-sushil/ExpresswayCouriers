package com.sushil.expressway.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private String contactPerson;

    @NotEmpty
    private String contactNumber;

    @NotNull(message = "Standard price per kg is required")
    private Double standardPricePerKg;

    @NotNull(message = "Premium price per kg is required")
    private Double premiumPricePerKg;

    @NotNull(message = "Surface price per kg is required")
    private Double surfacePricePerKg;

    private String address;
}
