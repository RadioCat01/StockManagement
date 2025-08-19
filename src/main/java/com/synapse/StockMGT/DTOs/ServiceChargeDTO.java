package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceChargeDTO {
    private String description;
    private Double chargeAmount;
}
