package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Store")
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Store extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    private String storeAddress;
    private String storeEmail;
    private String tel;
    private String mobile;
    private String businessRegNumber;

    @ManyToOne
    @JoinColumn(name = "subComId")
    @JsonBackReference
    private SubCompany subCompany;
}