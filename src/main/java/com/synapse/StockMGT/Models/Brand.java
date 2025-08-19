package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.CustomFields.Brand_Data;
import com.synapse.StockMGT.CustomFields.Brand_Fields;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;

    @Column(nullable = false, unique = true)
    private String brandName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    @JsonBackReference(value = "category-brand")
    private Category category;

    @OneToMany(mappedBy = "brand")
    private List<ItemInfo> itemInfos;

    @OneToMany
    @JoinColumn(name = "brandId")
    private List<SupplierGRN> supplierGRNs;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;

    @OneToMany(mappedBy = "brand")
    private List<Brand_Fields> fields;

    @OneToMany(mappedBy = "brand")
    private List<Brand_Data> data;
}
