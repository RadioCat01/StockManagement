package com.synapse.StockMGT.DTOs.FormDTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddFieldReqDTO {
    private int templateId;
    private List<FieldDTO> fields;
}
