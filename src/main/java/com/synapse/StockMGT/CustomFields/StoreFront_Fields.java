package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "StoreFrontFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreFront_Fields {
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
    @JoinColumn(name = "storeFrontId")
    @JsonIgnore
    private StoreFront storeFront;
}
