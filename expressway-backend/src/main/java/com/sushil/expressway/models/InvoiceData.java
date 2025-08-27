package com.sushil.expressway.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceData {
    private String tracking_number;
    private Date booking_date;
    private String service_type;
    private String receiver_address;
    private Integer weight;
    private Long total_amount;
}