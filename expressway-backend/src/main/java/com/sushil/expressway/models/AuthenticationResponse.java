package com.sushil.expressway.models;

import com.sushil.expressway.entitys.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {

    private String accessToken;
    private String message;
    private User user;

}
