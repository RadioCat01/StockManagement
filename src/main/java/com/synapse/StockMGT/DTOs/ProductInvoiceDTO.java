package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProductInvoiceDTO {
    private String productDescription;
    private String serials;
    private String warranty;
    private Double unitPrice;
    private int quantity;
    private Double total;
    private LocalDate invoiceDate;
    private LocalDate grnDate;
    private LocalDate supplierWarrantyUntil;
    private LocalDate sellerWarrantyUntil;
    private String remainingSellerWarranty;
    private String remainingSupplierWarranty;
}
