package com.synapse.StockMGT.CustomFields;

import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SubCompanyFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subcompany_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<SubCompany_Data> data;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
