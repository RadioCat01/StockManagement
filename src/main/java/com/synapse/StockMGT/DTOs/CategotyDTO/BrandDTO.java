package com.synapse.StockMGT.DTOs.CategotyDTO;

import com.synapse.StockMGT.DTOs.SupplierGRNDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Integer brandId;
    private String brandName;

    private List<ItemInfoDTO> itemInfos;
    private List<SupplierGRNDTO> supplierGRNs;
}
