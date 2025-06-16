package com.synapse.StockMGT.Configs;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class TemplatesInitializerConfig {
    private final Map<String, List<FieldDTO>> defaultTemplates = new HashMap<>();

    public TemplatesInitializerConfig() {
        defaultTemplates.put("Company", List.of(
                new FieldDTO(0,"companyName", "text","Company Name", true)
        ));

    }
}
