package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DrawerDTO {
    private String drawerName;
    private String drawerDescription;
    private String drawerType;
    private String drawerStatus;
    private int companyId;
    private int subCompanyId;
    private int counterId;
    private int drawerId;
}
