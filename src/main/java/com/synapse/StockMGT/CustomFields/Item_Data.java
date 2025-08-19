package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.Brand;
import com.synapse.StockMGT.Models.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ItemData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private Item_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Item item;
}
