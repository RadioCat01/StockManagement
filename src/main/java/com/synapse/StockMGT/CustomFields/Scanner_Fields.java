package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.Scanner;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ScanenrFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Scanner_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Scanner_Data> data;

    @ManyToOne
    @JoinColumn(name = "scannerId")
    @JsonIgnore
    private Scanner scanner;
}
