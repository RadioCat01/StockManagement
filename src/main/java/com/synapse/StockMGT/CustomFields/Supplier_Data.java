package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.Supplier;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SupplierData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Supplier_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private Supplier_Fields field;
}
