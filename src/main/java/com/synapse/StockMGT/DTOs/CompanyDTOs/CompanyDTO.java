package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CompanyDTO {
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    private String businessRegNumber;
    private String businessLogo;

    private Map<String, String> customFields;
}
