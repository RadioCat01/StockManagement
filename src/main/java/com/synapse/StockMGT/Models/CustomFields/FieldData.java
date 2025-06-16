package com.synapse.StockMGT.Models.CustomFields;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FieldData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    @ManyToOne
    @JoinColumn(name = "templateId")
    private Templates template;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private TemplateFields customField;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;
}
