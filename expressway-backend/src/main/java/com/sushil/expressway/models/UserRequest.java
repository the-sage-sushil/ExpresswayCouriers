package com.sushil.expressway.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull
    private String firstName;
    
    private String lastName;
    
    @NotNull
    @Column(unique = true)
    private String email;
    
    @NotNull
    private String password;

}
