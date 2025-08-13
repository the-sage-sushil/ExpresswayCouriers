package com.sushil.expressway.services;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.sushil.expressway.entitys.Consignment;

public class ConsignmentSpecifications {
    
    public static Specification<Consignment> hasClientId(Long clientId) {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null) {
                return criteriaBuilder.conjunction(); // Always true if clientId is null
            }
            return criteriaBuilder.equal(root.get("client").get("id"), clientId);
        };
    }
    
    public static Specification<Consignment> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
    
    public static Specification<Consignment> hasServiceType(String serviceType) {
        return (root, query, criteriaBuilder) -> {
            if (serviceType == null || serviceType.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("serviceType"), serviceType);
        };
    }
    
    public static Specification<Consignment> hasBookingDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("bookingDate"), startDate, endDate);
        };
    }
    
    public static Specification<Consignment> hasWeightBetween(Integer minWeight, Integer maxWeight) {
        return (root, query, criteriaBuilder) -> {
            if (minWeight == null || maxWeight == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("weight"), minWeight, maxWeight);
        };
    }
    
    // Add more specifications as needed...
}
