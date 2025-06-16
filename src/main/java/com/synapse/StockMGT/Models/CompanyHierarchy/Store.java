package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.Models.Item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Store")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST,  CascadeType.MERGE})
    @JoinTable(
            name = "store_storefront",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "storefront_id")
    )
    private List<StoreFront> storeFronts;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store")
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}