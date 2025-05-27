package com.synapse.StockMGT.Models;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice extends TenantAwareSupperClass{
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
    private List<Services> services = new ArrayList<>();
}
