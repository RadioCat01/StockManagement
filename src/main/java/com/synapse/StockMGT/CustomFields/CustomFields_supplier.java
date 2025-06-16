package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Enums.CustomFieldType;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CustomFields_supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomFields_supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fieldName;
    private String fieldValue;

    @Enumerated(EnumType.STRING)
    private CustomFieldType fieldType;

    private boolean enabled;
    private boolean required;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
