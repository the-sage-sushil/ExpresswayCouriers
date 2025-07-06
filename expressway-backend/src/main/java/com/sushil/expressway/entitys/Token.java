package com.sushil.expressway.entitys;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token {


    private String token;
    private String userId;
    private String userName;
    private String role;

}
