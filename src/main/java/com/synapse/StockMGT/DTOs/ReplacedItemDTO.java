package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReplacedItemDTO {
    private String description;
    private String serial;
}
