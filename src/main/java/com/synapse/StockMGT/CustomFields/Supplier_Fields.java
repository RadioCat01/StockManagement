package com.synapse.StockMGT.CustomFields;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.Supplier;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SupplierFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Supplier_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Supplier_Data> data;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;
}
