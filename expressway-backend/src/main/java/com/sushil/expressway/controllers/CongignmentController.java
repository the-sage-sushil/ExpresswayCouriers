package com.sushil.expressway.controllers;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.models.ConsignmentRequest;
import com.sushil.expressway.services.ConsignmentService;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;


@RequestMapping("consignments")
@RestController
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CongignmentController {

    private ConsignmentService consignmentService;

    @PostMapping("booking")
    public ResponseEntity<?> saveConsignment(@RequestBody ConsignmentRequest request) {
       return ResponseEntity.ok(consignmentService.save(request));
    }

    @GetMapping("bookings")
    public ResponseEntity<?> getBookings() {
        return ResponseEntity.ok(consignmentService.getBookings());
    }
}
