package com.sushil.expressway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.models.AuthenticationRequest;
import com.sushil.expressway.models.AuthenticationResponse;
import com.sushil.expressway.models.UserRequest;
import com.sushil.expressway.services.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication related APIs")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> registerUser(@RequestBody UserRequest request) {

        int userId = authenticationService.registerUser(request);
        return ResponseEntity.ok(userId);
    }
    
    @GetMapping("activate-token/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> activateToken(@PathVariable("token") String token) {
        return ResponseEntity.ok(authenticationService.activateToken(token));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {  
        return ResponseEntity.ok(authenticationService.login(request));
    }
    
}
