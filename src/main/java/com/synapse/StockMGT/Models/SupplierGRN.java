package com.synapse.StockMGT.Models;

import com.synapse.StockMGT.CustomFields.CustomFields_grn;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplierGRN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupplierGRN extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierGRNId;

    @Column(name = "supplierId")
    private int supplierId;
    private String categoryName;
    private String brandName;
    private String productDescription;

    private String warranty;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "GRNItemId")
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customId")
    @Builder.Default
    private List<CustomFields_grn> customFields = new ArrayList<>();
}
