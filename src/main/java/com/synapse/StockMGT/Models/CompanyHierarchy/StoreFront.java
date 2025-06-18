package com.synapse.StockMGT.Models.CompanyHierarchy;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "StoreFront")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreFront{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storefrontId;

    private String storeFrontName;

    @ManyToMany(mappedBy = "storeFronts")
    @JsonIgnore
    private List<Store> store;

    @OneToMany(mappedBy = "storeFront", cascade = CascadeType.ALL)
    private List<Counter>  counter;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    @JsonIgnore
    private SubCompany subCompany;
}
