package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCompanyDTO {
    private int companyId;
    private int subCompanyId;
    private String subCompanyName;
    private String subCompanyAddress;
    private String subCompanyPhone;
    private String subCompanyEmail;
    private String subBusinessRegNumber;
    private String subBusinessLogo;
}
