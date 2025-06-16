package com.synapse.StockMGT.Models.CustomFields;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ComMandatory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MandatoryFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyMandatoryId;

    private String fieldName;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "templateId")
    private Templates template;
}
