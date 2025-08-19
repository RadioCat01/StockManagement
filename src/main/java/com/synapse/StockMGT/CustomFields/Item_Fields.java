package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.Brand;
import com.synapse.StockMGT.Models.Item;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ItemFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Item_Data> data;

    @ManyToOne
    @JoinColumn(name = "itemId")
    @JsonIgnore
    private Item item;
}
