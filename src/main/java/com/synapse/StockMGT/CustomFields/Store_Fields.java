package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "StoreFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Store_Data> data;

    @ManyToOne
    @JoinColumn(name = "StoreId")
    @JsonIgnore
    private Store store;
}
