package com.synapse.StockMGT.DTOs.CompanyDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class POSTerminalDTO {
    private String posTerminalName;
    private String posTerminalDetails;
    private int companyId;
    private int subcompanyId;
    private int counterId;
    private int posId;
}
