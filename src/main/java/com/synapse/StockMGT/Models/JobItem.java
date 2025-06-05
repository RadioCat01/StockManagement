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
    private String serial;

    @Lob
    @Column(name = "defectiveDetails", columnDefinition = "TEXT")
    private String defectiveDetails;

    private String remainingWarranty;

    @Column(unique = true)
    private String barCode;
    private String barCodeImage;
}
