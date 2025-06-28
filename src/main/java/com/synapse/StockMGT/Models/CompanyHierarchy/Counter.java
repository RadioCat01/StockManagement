package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.CustomFields.Counter_Data;
import com.synapse.StockMGT.CustomFields.Counter_Fields;
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

    private String counterName;

    @Enumerated(EnumType.STRING)
    private CounterType  counterType;

    @ManyToOne
    @JoinColumn(name = "storeFrontId")
    @JsonIgnore
    private StoreFront storeFront;

    @OneToMany(mappedBy = "counter")
    private List<Counter_Data> data;

    @OneToMany(mappedBy = "counter", cascade = CascadeType.ALL)
    private List<Scanner> scanners;

    @OneToMany(mappedBy = "counter", cascade = CascadeType.ALL)
    private List<PosTerminal> posTerminals;

    @OneToMany(mappedBy = "counter",cascade = CascadeType.ALL)
    private List<CashDrawer> cashDrawers;

    @OneToMany(mappedBy = "counter")
    private List<Counter_Fields> fields;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    @JsonIgnore
    private SubCompany subCompany;
}
