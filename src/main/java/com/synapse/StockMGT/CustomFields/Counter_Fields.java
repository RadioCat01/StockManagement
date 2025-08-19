package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.Counter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CounterFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Counter_Fields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<Counter_Data> data;

    @ManyToOne
    @JoinColumn(name = "counterId")
    @JsonIgnore
    private Counter counter;
}
