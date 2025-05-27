package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaleItemDTO {
    private String brandName;
    private String itemCode;
    private Double dealerPrice;
    private Double retailPrice;
    private int quantity;
    private String description;
    private int supplierGRNID;
}
