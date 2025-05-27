package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Brand extends TenantAwareSupperClass{

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
}
