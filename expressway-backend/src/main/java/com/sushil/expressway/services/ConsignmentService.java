
package com.sushil.expressway.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sushil.expressway.common.Mapper;
import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentMapper;
import com.sushil.expressway.models.ConsignmentRequest;
import com.sushil.expressway.repositories.ConsignmentRepository;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;

@Service
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class ConsignmentService {

    private final Mapper mapper;
    private final ConsignmentMapper consignmentMapper;
    private final ConsignmentRepository consignmentRepository;

    public Long save(ConsignmentRequest request) {

        Consignment consignment = mapper.toConsignment(request);
        if (request.getClient() != null) {
            consignment.setClient((request.getClient()));
        }
        return consignmentRepository.save(consignment).getId();
    }

    @Transactional
    public Long update(ConsignmentRequest request, Long id) {

        Consignment existing = consignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

            // Check for unique tracking number violation
            if (request.getTrackingNumber() != null &&
                consignmentRepository.existsByTrackingNumberAndIdNot(request.getTrackingNumber(), id)) {
                throw new RuntimeException("Tracking number already exists for another consignment.");
            }

            consignmentMapper.updateEntityFromDto(request, existing);
            return consignmentRepository.save(existing).getId();
    }

    public List<Consignment> getBookings() {
        return consignmentRepository.findAll();
    }

    public Consignment getConsignmentById(Long id) {

        Consignment existingConsignment = consignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        ;

        return existingConsignment;
    }

    public List<Consignment> getConsignment(
            Long clientId,
            String status,
            String serviceType,
            String channelPartner,
            LocalDate bookingDateFrom,
            LocalDate bookingDateTo,
            Integer minWeight,
            Integer maxWeight,
            String paymentMode) {

        // Build the specification dynamically
        Specification<Consignment> spec = Specification.where(
                ConsignmentSpecifications.hasClientId(clientId));

        if (status != null && !status.isEmpty()) {
            spec = spec.and(ConsignmentSpecifications.hasStatus(status));
        }

        if (serviceType != null && !serviceType.isEmpty()) {
            spec = spec.and(ConsignmentSpecifications.hasServiceType(serviceType));
        }

        if (bookingDateFrom != null && bookingDateTo != null) {
            spec = spec.and(ConsignmentSpecifications.hasBookingDateBetween(bookingDateFrom, bookingDateTo));
        }

        if (minWeight != null && maxWeight != null) {
            spec = spec.and(ConsignmentSpecifications.hasWeightBetween(minWeight, maxWeight));
        }

        return consignmentRepository.findAll(spec);
    }

    public List<Consignment> getConsignmentsByClientAndDateRange(Long clientId, LocalDate fromDate, LocalDate toDate) {
        return consignmentRepository.findByClientIdAndBookingDateBetweenOrderByBookingDateAsc(clientId, fromDate, toDate);
    }

}
