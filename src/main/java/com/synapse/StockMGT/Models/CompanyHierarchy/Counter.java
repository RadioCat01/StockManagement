package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.synapse.StockMGT.Enums.CounterType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Counter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counterId;

    @Enumerated(EnumType.STRING)
    private CounterType  counterType;

    @ManyToOne
    @JoinColumn(name = "storeFrontId")
    private StoreFront storeFront;

    @OneToMany(mappedBy = "counter", cascade = CascadeType.ALL)
    private List<Scanner> scanners;

    @OneToMany(mappedBy = "counter", cascade = CascadeType.ALL)
    private List<PosTerminal> posTerminals;

    @OneToMany(mappedBy = "counter",cascade = CascadeType.ALL)
    private List<CashDrawer> cashDrawers;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
