package com.synapse.StockMGT.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class TransferReqDTO {
    private List<InventoryDTO> items;
    private int transferTo;
    private String reason;
}
