package com.sushil.expressway.controllers;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.models.ClientRequest;
import com.sushil.expressway.services.ClientService;

import jakarta.persistence.EntityListeners;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RequestMapping("client")
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@RestController
public class ClientController {

    private final ClientService clientService;

    @PostMapping()
    public ResponseEntity<?> saveClinet(@RequestBody @Valid ClientRequest request) {
        return ResponseEntity.ok(clientService.save(request));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getMethodName(@PathVariable("clientId") Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }
    
    
    

}
