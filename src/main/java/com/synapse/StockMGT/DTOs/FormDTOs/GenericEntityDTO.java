package com.synapse.StockMGT.DTOs.FormDTOs;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GenericEntityDTO {
    private String entityId;
    private int companyId;
    private int subcompanyId;
    private List<Integer> storeId;
    private int storefrontId;
    private int counterId;
    private String displayName;
    private Map<String, String> customFields;
}
