package com.synapse.StockMGT.CustomFields;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.Customers;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CustomerFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Customer_Data> data;

    @ManyToOne
    @JoinColumn(name = "custoemrId")
    private Customers customer;
}
