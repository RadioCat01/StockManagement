package com.synapse.StockMGT.CustomFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synapse.StockMGT.Models.CompanyHierarchy.PosTerminal;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "POSFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class POS_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<POS_Data> data;

    @ManyToOne
    @JoinColumn(name = "posId")
    @JsonIgnore
    private PosTerminal  posTerminal;
}
