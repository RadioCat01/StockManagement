package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import com.synapse.StockMGT.CustomFields.Supplier_Data;
import com.synapse.StockMGT.CustomFields.Supplier_Fields;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Supplier")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    @Column(name = "SupplierName")
    private String name;

    @Column(name = "Address")
    private String address;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "ContactName")
    private String contactName;

    @Column(name = "PaymentTerms")
    private String paymentTerms;

    @Column(name = "Period")
    private String period;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customId")
    @Builder.Default
    private List<CustomFields_supplier> customFields = new ArrayList<>();

    @OneToMany(mappedBy = "supplier")
    private List<Supplier_Fields> supplierFields;

    @OneToMany(mappedBy = "supplier")
    private List<Supplier_Data> supplierData;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
