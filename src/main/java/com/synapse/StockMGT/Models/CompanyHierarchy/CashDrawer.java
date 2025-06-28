package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.CustomFields.Drawer_Data;
import com.synapse.StockMGT.CustomFields.Drawer_Fields;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CashDrawer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CashDrawer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drawerId;

    private String drawerName;

    @OneToMany(mappedBy = "cashDrawer")
    private List<Drawer_Fields> fields;

    @OneToMany(mappedBy = "drawer")
    private List<Drawer_Data>  data;

    @ManyToOne
    @JoinColumn(name = "counterId")
    @JsonIgnore
    private Counter counter;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    @JsonIgnore
    private SubCompany subCompany;
}
