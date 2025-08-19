package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SubCompanyData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCompany_Data {
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
    private Subcompany_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private SubCompany  subCompany;
}
