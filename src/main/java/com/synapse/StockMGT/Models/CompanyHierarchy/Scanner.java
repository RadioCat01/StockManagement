package com.synapse.StockMGT.Models.CompanyHierarchy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.CustomFields.Scanner_Data;
import com.synapse.StockMGT.CustomFields.Scanner_Fields;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "scanner")
    private List<Scanner_Fields> fields;

    @OneToMany(mappedBy = "scanner")
    private List<Scanner_Data> data;

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
