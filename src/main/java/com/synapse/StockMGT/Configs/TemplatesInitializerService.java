package com.synapse.StockMGT.Configs;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import com.synapse.StockMGT.Models.CustomFields.TemplateFields;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import com.synapse.StockMGT.Repos.TemplateFieldsRepo;
import com.synapse.StockMGT.Repos.TemplateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplatesInitializerService {

    private final TemplateRepo templatesRepository;
    private final TemplateFieldsRepo templateFieldsRepository;
    private final TemplatesInitializerConfig templatesConfig;

    @PostConstruct
    public void initializeAllTemplates() {
        templatesConfig.getDefaultTemplates().forEach(this::initializeTemplateIfNotExists);
    }

    private void initializeTemplateIfNotExists(String templateType, List<FieldDTO> fieldDTOs) {
        if (templatesRepository.findByTemplateType(templateType).isPresent()) return;

        Templates template = Templates.builder()
                .templateType(templateType)
                .templateDescription("Default " + templateType + " Template")
                .build();
        templatesRepository.save(template);

        fieldDTOs.forEach(field -> {
            TemplateFields templateField = TemplateFields.builder()
                    .fieldName(field.getFieldName())
                    .fieldType(field.getFieldType())
                    .fieldQuestion(field.getFieldQuestion())
                    .isMandatory(field.isMandatory())
                    .template(template)
                    .build();
            templateFieldsRepository.save(templateField);
        });
    }
}

