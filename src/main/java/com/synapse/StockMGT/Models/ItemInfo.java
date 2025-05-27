package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itemInfo")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItemInfo extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer infoId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String itemDescription;

    @Column(unique = true)
    private String itemCode;

    @OneToMany(mappedBy = "itemInfo")
    @JsonManagedReference(value = "itemInfo-items")
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "brandID")
    @JsonIgnore
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customId")
    @Builder.Default
    private List<CustomFields_item> customFields = new ArrayList<>();
}
