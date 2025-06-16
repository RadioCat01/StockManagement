package com.synapse.StockMGT.Models.CustomFields;


import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CompanyTemplates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Templates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateId;

    private String templateType;
    private String templateDescription;

    @OneToMany(mappedBy = "template")
    private List<MandatoryFields>  companyMandatoryList;

    @OneToMany(mappedBy = "template")
    private List<CustomFields>   companyCustomList;

    @OneToMany(mappedBy = "template")
    private List<FieldData> actualData;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
