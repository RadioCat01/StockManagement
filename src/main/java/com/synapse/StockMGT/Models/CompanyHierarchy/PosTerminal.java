package com.synapse.StockMGT.Models.CompanyHierarchy;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PosTerminal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PosTerminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer posTerminalId;

    private String posTerminalName;
    private String posTerminalDetails;

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
