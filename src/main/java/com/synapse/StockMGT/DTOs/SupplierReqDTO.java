package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import lombok.Data;

import java.util.List;

@Data
public class SupplierReqDTO {
    private String name;
    private String address;
    private String contactNumber;
    private String contactName;
    private String period;
    private String paymentTerms;
    private List<CustomFields_supplier> customFields;
}
