package com.synapse.StockMGT.DTOs.FormDTOs;

import lombok.Data;

import java.util.Map;

@Data
public class DataReqDTO {
    private Map<String, Object> formData;
}
