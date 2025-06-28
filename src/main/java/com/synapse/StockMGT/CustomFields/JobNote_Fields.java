package com.synapse.StockMGT.CustomFields;


import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.JobNotes;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JobNoteFields")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobNote_Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;
    private String fieldType;
    private String fieldQuestion;
    private Boolean isMandatory;

    @OneToMany(mappedBy = "field")
    private List<JobNote_Data> data;

    @ManyToOne
    @JoinColumn(name = "jobNoteId")
    private JobNotes jobNote;
}
