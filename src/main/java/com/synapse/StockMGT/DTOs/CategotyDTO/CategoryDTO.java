package com.synapse.StockMGT.DTOs.CategotyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;

    private List<BrandDTO> brands;
}
