package com.synapse.StockMGT.Models;

import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Supplier")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Supplier extends TenantAwareSupperClass{
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
}
