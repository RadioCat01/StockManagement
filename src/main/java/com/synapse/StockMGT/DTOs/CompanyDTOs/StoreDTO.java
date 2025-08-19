package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreDTO {
    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;

    private int companyId;
    private int subCompanyId;
    private int storeId;
}
