package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReplacementNote")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplacementNote{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replacementNoteId;

    private String repNumber;
    private LocalDate replacementDate;

    private String customerName;
    private String customerPhone;

    @OneToOne
    @JoinColumn(name = "jobNotesId")
    private JobNotes jobNotes;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
