package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.Models.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierGRNDTO {
    private String categoryName;
    private String brandName;
    private String productDescription;

    private String warranty;
    private int quantity;
    private Double cost;
    private Double dealerPrice;
    private Double retailPrice;

    private String supplierPayment;
    private String paymentStatus;

    private String serialNumbers;
    private int storeId;

    private List<Item> items;
    private LocalDate grnDate;
    private String supplierInvoice;
}
