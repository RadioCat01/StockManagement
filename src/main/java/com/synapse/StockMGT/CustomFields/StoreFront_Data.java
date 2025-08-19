package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "StoreFrontData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreFront_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private StoreFront_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private StoreFront storeFront;
}
