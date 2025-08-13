package com.sushil.expressway.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;


public interface ConsignmentRepository extends JpaRepository<Consignment,Integer>, JpaSpecificationExecutor<Consignment> {

    void save(ConsignmentRequest request);

    

    @Query("""
        SELECT consignment 
        FROM Consignment consignment 
        WHERE consignment.client.id = :clientId
    """)
    /**
     * Finds all displayable books that are not archived, shareable, and not owned by the given user.
     *
     * @param pageable the pagination information
     * @param clientId the ID of the user to exclude as the owner
     * @return a page of displayable books
     */
    List<Consignment> findAllConsignmentByClientId(@Param("clientId") Long clientId);

    // Alternative approach using @Query with optional parameters (less flexible)
    @Query("""
        SELECT c FROM Consignment c 
        WHERE c.client.id = :clientId 
        AND (:status IS NULL OR c.status = :status)
        AND (:serviceType IS NULL OR c.serviceType = :serviceType)
        AND (:channelPartner IS NULL OR c.chennalPatner = :channelPartner)
        AND (:bookingDateFrom IS NULL OR c.bookingDate >= :bookingDateFrom)
        AND (:bookingDateTo IS NULL OR c.bookingDate <= :bookingDateTo)
        AND (:minWeight IS NULL OR c.weight >= :minWeight)
        AND (:maxWeight IS NULL OR c.weight <= :maxWeight)
    """)
    List<Consignment> findConsignmentsByFilters(
        @Param("clientId") Long clientId,
        @Param("status") String status,
        @Param("serviceType") String serviceType,
        @Param("channelPartner") String channelPartner,
        @Param("bookingDateFrom") LocalDate bookingDateFrom,
        @Param("bookingDateTo") LocalDate bookingDateTo,
        @Param("minWeight") Integer minWeight,
        @Param("maxWeight") Integer maxWeight
    );

}
