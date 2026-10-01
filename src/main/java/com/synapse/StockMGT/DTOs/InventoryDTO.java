package com.synapse.StockMGT.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class InventoryDTO {
    private int itemId;
    private String brand;
    private String itemCode;
    private String description;
    private String productSerial;
    private String category;
    private int storeId;
    private LocalDate grnDate;
    private LocalDate lastUpdate;
    private String currentPosition;
    private String stockType;
}
