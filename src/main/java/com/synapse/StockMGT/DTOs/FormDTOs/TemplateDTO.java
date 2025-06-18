package com.synapse.StockMGT.DTOs.FormDTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateDTO {
    private int templateId;
    private String templateType;
    private String templateDescription;
}
