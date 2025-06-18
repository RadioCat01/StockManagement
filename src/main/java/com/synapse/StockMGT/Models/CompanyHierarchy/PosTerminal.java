package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
