package com.sushil.expressway.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.services.ConsignmentService;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@RestController
@RequestMapping("reports")
public class ReportController {

    private final ConsignmentService consignmentService;

    @GetMapping("{client_id}")
    public ResponseEntity<List<Consignment>> getConsignmentsByClientId(
            @PathVariable Long clientId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String serviceType,
            @RequestParam(required = false) String channelPartner,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDateTo,
            @RequestParam(required = false) Integer minWeight,
            @RequestParam(required = false) Integer maxWeight,
            @RequestParam(required = false) String paymentMode) {
        
        List<Consignment> consignments = consignmentService.getConsignment(
            clientId, status, serviceType, channelPartner,
            bookingDateFrom, bookingDateTo, minWeight, maxWeight, paymentMode
        );
        return ResponseEntity.ok(consignments);
    }
    
}
