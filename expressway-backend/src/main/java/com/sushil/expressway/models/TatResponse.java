package com.sushil.expressway.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TatResponse {
    private String status;
    private List<Object> data;
    private List<Object> explain;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceData {
        private String serviceType;
        private String period;
        private int additionalPrice;
        private String price;
        private String priceWithRiskSurcharge;
        private String priceWithCarrierRiskSurcharge;
        private String priceWithOwnerRiskSurcharge;
        private String priceWithOptionalInsurance;
        private String serviceCode;
        private int volumetricWeight;
        private int volumetricWeights;
        private int gstRate;
        private boolean mbgApplicable;
        private String mbgMessage;
        private int mbgVasCharge;
        private int basePriceForDiscount;
        private String GST;
        private String GSTWithRiskSurcharge;
        private String GSTWithCarrierRiskSurcharge;
        private String GSTWithOwnerRiskSurcharge;
        private int chargeableWeight;
        private String edd; // Consider using java.time.LocalDate if possible
        private int order;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceExplain {
        private String serviceType;
        private DestinationType destinationType;
        private PriceData priceData;
        private String source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DestinationType {
            private String _id;
            private String code;
            private String id;
            private String name;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PriceData {
            private int ebrCCRate;
            private int basePrice;
            private int basePriceEFR;
            private int internationalCES;
            private int ondcPrice;
            private int finalPrice;
        }
    }
}

