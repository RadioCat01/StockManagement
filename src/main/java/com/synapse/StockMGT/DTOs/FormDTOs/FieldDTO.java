package com.synapse.StockMGT.DTOs.FormDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldDTO {
    private String fieldName;
    private String fieldLabel;
    private String fieldType;
    private boolean isFixedField;
    private boolean required;

    private List<String> options;     
}
