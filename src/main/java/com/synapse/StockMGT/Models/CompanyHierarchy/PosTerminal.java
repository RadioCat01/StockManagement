package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.CustomFields.POS_Data;
import com.synapse.StockMGT.CustomFields.POS_Fields;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "posTerminal")
    private List<POS_Fields> fields;

    @OneToMany(mappedBy = "posTerminal")
    private List<POS_Data> data;

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
