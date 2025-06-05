package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SalesReportDTO {
    private String brand;
    private String description;
    private String itemCode;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private LocalDate soldDate;
    private String serialNumbers;
    private String storeName;
    private String storeAddress;
    private String poReference;
    private String invoiceNumber;
}
