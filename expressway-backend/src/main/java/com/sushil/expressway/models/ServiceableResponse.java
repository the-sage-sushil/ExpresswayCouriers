
package com.sushil.expressway.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceableResponse {
    private String status;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private boolean serviceable;
        private String destinationBranchCity;
        private String state;
        private String err;
        private boolean isMBGApplicable;
    }
}
