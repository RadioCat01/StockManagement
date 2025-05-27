package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

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
}
