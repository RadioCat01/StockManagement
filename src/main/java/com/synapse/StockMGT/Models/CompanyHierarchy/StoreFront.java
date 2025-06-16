package com.synapse.StockMGT.Models.CompanyHierarchy;


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

    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;

    @ManyToMany(mappedBy = "storeFronts")
    private List<Store> store;

    @OneToMany(mappedBy = "storeFront", cascade = CascadeType.ALL)
    private List<Counter>  counter;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
