package com.synapse.StockMGT.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class SoldProductDTO {
    private String itemCode;
    private int supplierGRNID;
    private int selectedQuantity;
}
