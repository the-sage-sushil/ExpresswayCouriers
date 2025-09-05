package com.sushil.expressway.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;
import com.sushil.expressway.models.ServiceableResponse;
import com.sushil.expressway.models.TatRequest;
import com.sushil.expressway.services.ConsignmentService;
import com.sushil.expressway.services.ReportService;
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
    private ReportService reportService;

    @PostMapping("booking")
    public ResponseEntity<?> saveConsignment(@RequestBody ConsignmentRequest request) {
       return ResponseEntity.ok(consignmentService.save(request));
    }

    @PutMapping("booking/{clientId}")
    public ResponseEntity<?> saveConsignment(
        @PathVariable Long clientId,
        @RequestBody ConsignmentRequest request) {
       return ResponseEntity.ok(consignmentService.update(request, clientId));
    }
    
    @GetMapping("/consignmentbyClientId/{clientId}")
    public ResponseEntity<?> getConsignmentsByClientId(
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
    
    @GetMapping("bookings")
    public ResponseEntity<List<Consignment>> getConsignment(
            @RequestParam(required = false) Long clientId,
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
    
    @GetMapping("serviceable/{destPincode}")
    public Mono<ServiceableResponse> getService(@PathVariable("destPincode") int pincode) {
        return utilService.getService(pincode);
    }
    
    @PostMapping("/getTat")
    public Mono<Object> getTat(@RequestBody TatRequest request) {
        return utilService.getTat(request);
    }
    
    @GetMapping("/invoice/{clientId}")
    public ResponseEntity<byte[]> generateInvoice(
        @PathVariable Long clientId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        
        try {
            byte[] pdfBytes = reportService.generateInvoicePdf(clientId, fromDate, toDate);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=invoice.pdf")
                .body(pdfBytes);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
