package com.synapse.StockMGT.DTOs.FormDTOs;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {
    private int templateId;
    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private boolean isMandatory;
}
