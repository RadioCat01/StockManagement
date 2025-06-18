package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import com.synapse.StockMGT.DTOs.FormDTOs.TemplateDTO;
import com.synapse.StockMGT.Models.CustomFields.TemplateFields;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import com.synapse.StockMGT.Repos.TemplateFieldsRepo;
import com.synapse.StockMGT.Repos.TemplateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormService {
    private final TemplateRepo templateRepo;
    private final TemplateFieldsRepo templateFieldsRepo;

    public List<TemplateDTO> getTemplates() {
        return templateRepo.findAll().stream().map(template -> TemplateDTO.builder()
                        .templateId(template.getTemplateId())
                        .templateType(template.getTemplateType())
                        .templateDescription(template.getTemplateDescription())
                        .build())
                .toList();
    }

    public List<FieldDTO> getGenericFields(String fieldName) {
        Templates template = templateRepo.findByTemplateType(fieldName)
                .orElseThrow();
        return template.getTemplateFields().stream().map(field ->
                FieldDTO.builder()
                        .templateId(template.getTemplateId())
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()).toList();
    }

    public List<FieldDTO> addGenericFields(FieldDTO fieldDTO) {
        Templates template = templateRepo.findById(fieldDTO.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid template ID: " + fieldDTO.getTemplateId()));
        TemplateFields field = TemplateFields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .isMandatory(fieldDTO.isMandatory())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .template(template)
                .build();
        templateFieldsRepo.save(field);

        List<TemplateFields> updatedFields = templateFieldsRepo.findByTemplate_TemplateId((fieldDTO.getTemplateId()));

        return updatedFields.stream().map(f -> FieldDTO.builder()
                .templateId(f.getTemplate().getTemplateId())
                .fieldName(f.getFieldName())
                .fieldType(f.getFieldType())
                .isMandatory(f.getIsMandatory())
                .build()).collect(Collectors.toList());
    }

}