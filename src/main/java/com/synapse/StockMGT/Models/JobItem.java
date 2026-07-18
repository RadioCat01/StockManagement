package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "JobItems")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobItemId;

    private String description;
    @Column(unique = true)
    private String serial;

    @Column(name = "defectiveDetails", columnDefinition = "TEXT")
    private String defectiveDetails;

    private String remainingSellerWarranty;
    private String remainingSupplierWarranty;

    @Column(unique = true)
    private String barCode;
    private String barCodeImage;

    private boolean isWarrantyClaimed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    private ReplacedItem replacedItem;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subComId")
    private SubCompany subCompany;
}
