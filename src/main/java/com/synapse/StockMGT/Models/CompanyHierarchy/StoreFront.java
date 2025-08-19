package com.synapse.StockMGT.Models.CompanyHierarchy;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.CustomFields.StoreFront_Data;
import com.synapse.StockMGT.CustomFields.StoreFront_Fields;
import com.synapse.StockMGT.User.User;
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

    @OneToMany(mappedBy = "storeFront")
    private List<StoreFront_Fields> fields;

    @OneToMany(mappedBy = "storeFront")
    private List<StoreFront_Data> data;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    @JsonIgnore
    private SubCompany subCompany;

    @OneToOne(mappedBy = "storeFront")
    private User user;
}
