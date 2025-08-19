package com.synapse.StockMGT.DTOs.CompanyDTOs;

import com.synapse.StockMGT.Enums.CounterType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CounterDTO {
    private CounterType counterType;

    private int storeFrontId;
    private int companyId;
    private int subCompanyId;
    private int counterId;
}
