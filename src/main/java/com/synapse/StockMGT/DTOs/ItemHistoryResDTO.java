package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.Models.Item;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ItemHistoryResDTO {
    private String brand;
    private String itemCode;
    private String serialNo;
    private String currentState;
    private LocalDate lastUpdate;
}
