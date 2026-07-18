package com.synapse.StockMGT.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Transfers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transfers{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    private String transferNumber;
    private LocalDate transferDate;
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String serials;
    private String transferFrom;
    private String transferTo;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
