package com.synapse.StockMGT.Models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ItemHistory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemHistory extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;

    private String brand;
    private String itemCode;
    private String serialNo;
    private String currentState;
    private LocalDate lastUpdate;;
}
