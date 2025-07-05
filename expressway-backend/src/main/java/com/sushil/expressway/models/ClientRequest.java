package com.sushil.expressway.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {

    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Double defaultPricePerKg; // or whatever pricing schema you have

    private String address;

}
