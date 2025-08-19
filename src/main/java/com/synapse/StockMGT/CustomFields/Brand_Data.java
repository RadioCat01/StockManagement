package com.synapse.StockMGT.CustomFields;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.Brand;
import com.synapse.StockMGT.Models.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BrandData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand_Data {
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
    private Brand_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Brand brand;
}
