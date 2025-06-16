package com.synapse.StockMGT.Models.CustomFields;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FieldOptions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private Long tableFieldId;
    private String fieldType;
    private String optionValue;
}
