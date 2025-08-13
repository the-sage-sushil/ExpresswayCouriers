package com.sushil.expressway.services;

import java.util.List;

import org.hibernate.mapping.Any;
import org.springframework.stereotype.Service;

import com.sushil.expressway.entitys.Consignment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

    private ConsignmentService consignmentService;

    public Object GetBillforClinet(Long client_id, Any filter) {
        List<Consignment> resposne = consignmentService.getConsignment(client_id, null, null, null, null, null, null, null, null);
        return List.of();
    }

}

