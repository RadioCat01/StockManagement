package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.synapse.StockMGT.CustomFields.*;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SubCompany")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCompany{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCompanyId;

    private String subCompanyName;
    private String subCompanyAddress;
    private String subCompanyPhone;
    private String subCompanyEmail;
    private String subBusinessRegNumber;
    private String subBusinessLogo;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<Store> stores;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<StoreFront> storeFronts;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<Counter> counters;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<Scanner> scanners;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<CashDrawer> cashDrawers;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    private List<PosTerminal> posTerminals;

    @OneToMany(mappedBy = "subCompany")
    private List<Brand> brands;

    @OneToMany(mappedBy = "subCompany")
    private List<Category> categories;

    @OneToMany(mappedBy = "subCompany")
    private List<Customers> customers;

    @OneToMany(mappedBy = "subCompany")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "subCompany")
    private List<Item> items;

    @OneToMany(mappedBy = "subCompany")
    private List<ItemHistory> itemHistories;

    @OneToMany(mappedBy = "subCompany")
    private List<ItemInfo> itemInfos;

    @OneToMany(mappedBy = "subCompany")
    private List<JobItem> jobItems;

    @OneToMany(mappedBy = "subCompany")
    private List<JobNotes> jobNotes;

    @OneToMany(mappedBy = "subCompany")
    private List<ReplacedItem> replacedItems;

    @OneToMany(mappedBy = "subCompany")
    private List<ReplacementNote> replacementNotes;

    @OneToMany(mappedBy = "subCompany")
    private List<Sales> sales;

    @OneToMany(mappedBy = "subCompany")
    private List<Services> services;

    @OneToMany(mappedBy = "subCompany")
    private List<SoldItem> soldItems;

    @OneToMany(mappedBy = "subCompany")
    private List<SoldProducts> soldProducts;

    @OneToMany(mappedBy = "subCompany")
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "subCompany")
    private List<SupplierGRN> supplierGRNS;

    @OneToMany(mappedBy = "subCompany")
    private List<Transfers> transfers;

    @OneToMany(mappedBy = "subCompany")
    private List<CustomFields_grn> customFieldsGRNs;

    @OneToMany(mappedBy = "subCompany")
    private List<CustomFields_customer> customFieldsCustomers;

    @OneToMany(mappedBy = "subCompany")
    private List<CustomFields_item> customFieldsItems;

    @OneToMany(mappedBy = "subCompany")
    private List<CustomFields_jobs> customFieldsJobs;

    @OneToMany(mappedBy = "subCompany")
    private List<CustomFields_supplier> customFieldsSuppliers;

    @OneToMany(mappedBy = "subCompany")
    private List<Templates> templates;
}
