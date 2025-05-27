package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_item;
import lombok.Data;

import java.util.List;

@Data
public class BrandDTO {
    private int categoryId;
    private String brandName;

    private String productDescription;
    private String itemCode;

    private List<CustomFields_item> customFields;
}
