package com.synapse.StockMGT.Models.CustomFields;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ComData")
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

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;
}
