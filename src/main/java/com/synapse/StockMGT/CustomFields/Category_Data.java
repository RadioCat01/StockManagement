package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.Category;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CategoryData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category_Data {
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
    private Category_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Category category;
}
