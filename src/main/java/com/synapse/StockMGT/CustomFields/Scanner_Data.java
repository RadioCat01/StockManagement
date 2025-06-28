package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Scanner;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ScannerData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Scanner_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private Scanner_Fields field;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Scanner scanner;
}
