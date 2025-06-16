package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.Models.JobNotes;
import com.synapse.StockMGT.Models.ReplacementNote;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class JobItemsDTO {
    private String description;
    private String serial;
    private String defectiveDetails;
    private String barcodeImage;
    private String remainingSellerWarranty;
    private String remainingSupplierWarranty;
    private boolean isWarrantyClaimed;
    private ReplacedItemDTO replacedItem;
    private String customerName;
    private String customerPhone;
    private LocalDate returnedDate;
}

