package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Sales")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sales{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customers customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id")
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<SoldProducts> soldProducts;

    private String saleType;
    @Builder.Default
    private LocalDate soldDate=LocalDate.now();

    private String poReference;
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
