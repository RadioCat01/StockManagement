package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(unique = true)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "info_id")
    @JsonBackReference(value = "itemInfo-items")
    private ItemInfo itemInfo;

    private Double cost;
    private Double dealerPrice;
    private Double retailPrice;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    private LocalDate lastUpdate;
    private String currentPosition;

    private String stockType;
}
