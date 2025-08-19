package com.synapse.StockMGT.DTOs.CategotyDTO;

import com.synapse.StockMGT.DTOs.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoDTO {
    private Integer infoId;
    private String itemDescription;
    private String itemCode;

    private List<ItemDTO> items;
}
