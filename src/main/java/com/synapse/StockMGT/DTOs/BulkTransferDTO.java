package com.synapse.StockMGT.DTOs;

import lombok.Data;

@Data
public class BulkTransferDTO {
    private int storeId;
    private String itemCode;
    private int itemQuantity;
    private String reason;
    private String brand;
}
