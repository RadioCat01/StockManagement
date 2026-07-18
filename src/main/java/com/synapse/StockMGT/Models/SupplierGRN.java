package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.CustomFields.CustomFields_grn;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplierGRN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierGRN{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierGRNId;

    @Column(name = "supplierId")
    private int supplierId;
    private String categoryName;
    private String brandName;
    private String productDescription;

    private String warranty;
    private String sellerWarranty;
    private int quantity;
    private Double cost;
    private Double dealerPrice;
    private Double retailPrice;

    private String supplierPayment;
    private String paymentStatus;
    private String itemCode;
    private String supplierInvoiceNumber;
    private LocalDate grnDate;
    private String serialNumberList;
    private int store;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierGRN")
    @Builder.Default
    @JsonManagedReference(value = "grn")
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customId")
    @Builder.Default
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<CustomFields_grn> customFields = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
