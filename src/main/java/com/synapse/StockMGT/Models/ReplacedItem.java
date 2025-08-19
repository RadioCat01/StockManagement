package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ReplacedItem")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplacedItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replacedItemId;

    private String description;
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
