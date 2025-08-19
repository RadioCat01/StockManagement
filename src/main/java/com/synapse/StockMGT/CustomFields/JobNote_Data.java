package com.synapse.StockMGT.CustomFields;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.JobNotes;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "JobNoteData")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobNote_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;

    private String entityId;
    private String formType;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private JobNotes jobNote;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    @JsonBackReference
    private JobNote_Fields field;
}
