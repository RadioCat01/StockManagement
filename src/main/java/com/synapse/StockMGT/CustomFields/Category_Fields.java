package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.Category;
import com.synapse.StockMGT.Models.CompanyHierarchy.Scanner;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CategoryFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Category_Data> data;

    @ManyToOne
    @JoinColumn(name = "categotyId")
    @JsonIgnore
    private Category category;
}
