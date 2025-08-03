package com.sushil.expressway.controllers;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.models.ConsignmentRequest;
import com.sushil.expressway.models.ServiceableResponse;
import com.sushil.expressway.models.TatRequest;
import com.sushil.expressway.models.TatResponse;
import com.sushil.expressway.services.ConsignmentService;
import com.sushil.expressway.services.UtilService;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;



@RequestMapping("consignments")
@RestController
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CongignmentController {

    private ConsignmentService consignmentService;
    private UtilService utilService;

    @PostMapping("booking")
    public ResponseEntity<?> saveConsignment(@RequestBody ConsignmentRequest request) {
       return ResponseEntity.ok(consignmentService.save(request));
    }
    
    @GetMapping("bookings")
    public ResponseEntity<?> getBookings() {
        return ResponseEntity.ok(consignmentService.getBookings());
    }
    
    @GetMapping("/consignmentbyClientId/{clientId}")
    public ResponseEntity<?> getMethodName( @PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(consignmentService.getConsignmentByClientId(clientId));
    }
    
    @GetMapping("serviceable/{destPincode}")
    public Mono<ServiceableResponse> getService(@PathVariable("destPincode") int pincode) {
        return utilService.getService(pincode);
    }
    @PostMapping("getTat")
    public Mono<TatResponse> getTat(@RequestBody TatRequest request) {
        return utilService.getTat(request);
    }
}
