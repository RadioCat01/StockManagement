package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Sold_Items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SoldItem extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldItemId;
    private String itemCode;
    private String serialNumber;
    private int supplierGRNId;
}
