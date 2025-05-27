package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StoreDTO {
    private Integer storeId;

    private String storeName;
    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;
}
