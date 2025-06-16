package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScannerDTO {
    private String scannerName;
    private String scannerSerial;
    private int companyId;
    private int subcompanyId;
    private int counterId;
    private int scannerId;
}
