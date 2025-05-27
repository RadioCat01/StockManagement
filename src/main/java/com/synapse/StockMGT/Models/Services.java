package com.synapse.StockMGT.Models;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Table(name = "Services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Services extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    private String serviceDescription;
    private Double chargeAmount;
}
