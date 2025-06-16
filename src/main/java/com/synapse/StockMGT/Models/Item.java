package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(unique = true)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "GRNItemId")
    @JsonBackReference(value = "grn")
    private SupplierGRN supplierGRN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "info_id")
    @JsonBackReference(value = "itemInfo-items")
    private ItemInfo itemInfo;

    private Double cost;
    private Double dealerPrice;
    private Double retailPrice;

    @ManyToOne
    @JoinColumn(name = "storeId")
    @JsonBackReference(value = "store")
    private Store store;

    private LocalDate lastUpdate;
    private String currentPosition;

    private String stockType;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
