package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Scanner")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Scanner{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scannerId;

    private String scannerName;
    private String scannerSerial;

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
