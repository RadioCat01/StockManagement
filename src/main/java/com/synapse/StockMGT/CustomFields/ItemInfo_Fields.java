package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.Scanner;
import com.synapse.StockMGT.Models.ItemInfo;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ItemInfoFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemInfo_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<ItemInfo_Data> data;

    @ManyToOne
    @JoinColumn(name = "infoId")
    @JsonIgnore
    private ItemInfo itemInfo;
}
