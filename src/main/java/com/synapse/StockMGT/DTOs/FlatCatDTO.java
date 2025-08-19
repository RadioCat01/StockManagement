package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class FlatCatDTO {
    private String categoryName;
    private String brandName;
    private String itemCode;
    private String itemDescription;
    private int quantity;
    private LocalDate date;
}
