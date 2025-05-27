package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SoldProducts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SoldProducts extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldProductId;
    private String itemCode;
    private int supplierGRNId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="SoldProductID")
    @Builder.Default
    private List<SoldItem> soldItems = new ArrayList<>();
}
