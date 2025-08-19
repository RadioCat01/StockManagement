package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResDTO {
    private Integer supplierId;
    private String name;
    private String address;
    private String contactNumber;
    private String contactName;
    private String paymentTerms;
    private String period;

    private String vatNumber;
    private String bankDetails;
}
