package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ReplacedItem")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ReplacedItem extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replacedItemId;

    private String description;
    private String serialNumber;
}
