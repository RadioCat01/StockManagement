package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CompanyDetails")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    private String businessRegNumber;
    private String businessLogo;
}
