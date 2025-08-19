package com.synapse.StockMGT.CustomFields;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CompanyFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Company_Data> data;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
}
