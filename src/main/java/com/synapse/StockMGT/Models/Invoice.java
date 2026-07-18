package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Invoice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Invoice{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    private String customerName;
    private String customerPhone;
    private String customerAddress;

    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String paymentTerms;
    private String salesPerson;

    private double subTotal;
    private double vat;
    private double totalInvoice;
    private LocalDate poDate;
    private String poReference;

    @OneToOne
    @JoinColumn(name = "saleId", nullable = false)
    private Sales sales;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoiceId")
    @Builder.Default
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<Services> services = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
