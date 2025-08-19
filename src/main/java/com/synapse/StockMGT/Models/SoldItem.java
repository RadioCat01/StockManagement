package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Sold_Items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoldItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldItemId;
    private String itemCode;
    private String serialNumber;
    private int supplierGRNId;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
