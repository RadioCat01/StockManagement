package com.synapse.StockMGT.Models.CompanyHierarchy;

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
    private Counter counter;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
