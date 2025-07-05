package com.sushil.expressway.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sushil.expressway.entitys.Consignment;
import com.sushil.expressway.models.ConsignmentRequest;


public interface ConsignmentRepository extends JpaRepository<Consignment,Integer> {

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

}
