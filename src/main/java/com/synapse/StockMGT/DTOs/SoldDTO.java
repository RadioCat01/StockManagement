package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_customer;
import lombok.Data;

import java.util.List;

@Data
public class SoldDTO {
    private List<SoldProductDTO> products;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String paymentTerms;
    private Boolean retail;
    private Double vat;
    private String poNumber;
    private String poReference;
    private List<ServiceChargeDTO> serviceCharges;
    private List<CustomFields_customer> customFields;
}
