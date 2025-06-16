package com.synapse.StockMGT.Models.CustomFields;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TemplateFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TemplateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @ManyToOne
    @JoinColumn(name = "templateId")
    private Templates template;

    @OneToMany(mappedBy = "customField")
    private List<FieldData> fieldDataList;
}
