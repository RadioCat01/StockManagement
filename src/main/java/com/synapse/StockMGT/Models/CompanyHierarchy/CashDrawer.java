package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
