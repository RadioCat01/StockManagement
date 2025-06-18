package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.synapse.StockMGT.CustomFields.*;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Company")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<SubCompany> subCompanies;

    @OneToMany(mappedBy = "company")
    private List<Store> stores;

    @OneToMany(mappedBy = "company")
    private List<StoreFront> storeFronts;

    @OneToMany(mappedBy = "company")
    private List<Counter> counters;

    @OneToMany(mappedBy = "company")
    private List<Scanner> scanners;

    @OneToMany(mappedBy = "company")
    private List<PosTerminal> posTerminals;

    @OneToMany(mappedBy = "company")
    private List<CashDrawer> cashDrawers;

    @OneToMany(mappedBy = "company")
    private List<Brand> brands;

    @OneToMany(mappedBy = "company")
    private List<Category> categories;

    @OneToMany(mappedBy = "company")
    private List<Customers> customers;

    @OneToMany(mappedBy = "company")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "company")
    private List<Item> items;

    @OneToMany(mappedBy = "company")
    private List<ItemHistory> itemHistories;

    @OneToMany(mappedBy = "company")
    private List<ItemInfo> itemInfos;

    @OneToMany(mappedBy = "company")
    private List<JobItem> jobItems;

    @OneToMany(mappedBy = "company")
    private List<JobNotes> jobNotes;

    @OneToMany(mappedBy = "company")
    private List<ReplacedItem> replacedItems;

    @OneToMany(mappedBy = "company")
    private List<ReplacementNote> replacementNotes;

    @OneToMany(mappedBy = "company")
    private List<Sales> sales;

    @OneToMany(mappedBy = "company")
    private List<Services> services;

    @OneToMany(mappedBy = "company")
    private List<SoldItem> soldItems;

    @OneToMany(mappedBy = "company")
    private List<SoldProducts> soldProducts;

    @OneToMany(mappedBy = "company")
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "company")
    private List<SupplierGRN> supplierGRNS;

    @OneToMany(mappedBy = "company")
    private List<Transfers> transfers;

    @OneToMany(mappedBy = "company")
    private List<CustomFields_grn> customFieldsGRNs;

    @OneToMany(mappedBy = "company")
    private List<CustomFields_customer> customFieldsCustomers;

    @OneToMany(mappedBy = "company")
    private List<CustomFields_item> customFieldsItems;

    @OneToMany(mappedBy = "company")
    private List<CustomFields_jobs> customFieldsJobs;

    @OneToMany(mappedBy = "company")
    private List<CustomFields_supplier> customFieldsSuppliers;

    @OneToMany(mappedBy = "company")
    private List<Templates> templates;
}
