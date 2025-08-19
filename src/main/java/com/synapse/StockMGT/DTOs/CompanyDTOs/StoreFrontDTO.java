package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StoreFrontDTO {
    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;

    private List<Integer> storeIds;
    private int subCompanyId;
    private int companyId;
    private int storeFrontId;
}
