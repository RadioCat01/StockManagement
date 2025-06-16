package com.synapse.StockMGT.Models.CompanyHierarchy;

import lombok.*;

import javax.persistence.*;

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
    private String drawerDescription;
    private String drawerType;
    private String drawerStatus;

    @ManyToOne
    @JoinColumn(name = "counterId")
    private Counter counter;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
