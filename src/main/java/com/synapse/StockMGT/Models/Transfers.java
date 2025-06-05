package com.synapse.StockMGT.Models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Transfers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Transfers extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    private String transferNumber;
    private LocalDate transferDate;
    private String reason;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String serials;
    private String transferFrom;
    private String transferTo;
}
