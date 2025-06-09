package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "JobItems")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class JobItem extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobItemId;

    private String description;
    @Column(unique = true)
    private String serial;

    @Lob
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
}
