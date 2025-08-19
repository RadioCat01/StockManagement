package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.Customers;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CustomerData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private Customer_Fields field;
}
