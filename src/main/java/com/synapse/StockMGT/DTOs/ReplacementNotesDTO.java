package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReplacementNotesDTO {
    private String repNumber;
    private LocalDate replacementDate;
    private String defectItemDescription;
    private String defectItemSerial;
    private String replacedItemDescription;
    private String replacedItemSerial;
    private String customerName;
    private String customerPhone;
}
