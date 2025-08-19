package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CompanyData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private Company_Fields field;
}
