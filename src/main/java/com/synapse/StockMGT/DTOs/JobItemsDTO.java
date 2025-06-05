package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobItemsDTO {
    private String description;
    private String serial;
    private String defectiveDetails;
    private String remainingWarranty;
    private String barcodeImage;
}
