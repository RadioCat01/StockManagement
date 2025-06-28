package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.CashDrawer;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DrawerFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Drawer_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Drawer_Data> data;

    @ManyToOne
    @JoinColumn(name = "drawerId")
    @JsonIgnore
    private CashDrawer cashDrawer;
}
