package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SoldProducts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoldProducts{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldProductId;
    private String itemCode;
    private int supplierGRNId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="SoldProductID")
    @Builder.Default
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<SoldItem> soldItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "storeId")
    private Store store;
    private String sellerWarranty;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
