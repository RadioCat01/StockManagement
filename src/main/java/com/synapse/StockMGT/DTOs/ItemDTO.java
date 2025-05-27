package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_grn;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ItemDTO {
    private int categoryId;
    private int brandId;
    private int supplierId;
    private String itemCode;
    private String warranty;
    private int quantity;
    private Double itemCost;
    private Double dealerPrice;
    private Double retailPrice;
    private String paymentStatus;
    private String supplierPayment;
    private Boolean hasSerialNumbers;
    private String supplierInvoiceNumber;
    private String grnDate;
    private int store;
    private List<String> serialNumbers;

    private List<CustomFields_grn> customFields;
}
