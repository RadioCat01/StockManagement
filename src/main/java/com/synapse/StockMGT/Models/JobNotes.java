package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.synapse.StockMGT.CustomFields.CustomFields_customer;
import com.synapse.StockMGT.CustomFields.CustomFields_jobs;
import com.synapse.StockMGT.Enums.JobStatus;
import com.synapse.StockMGT.Enums.JobTypes;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "JobNotes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class JobNotes extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobNotesId;

    private String jobNumber;
    private LocalDate jobDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobNoteId")
    @Builder.Default
    private List<JobItem> jobItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private JobTypes jobType;

    private LocalDate invoicedDate;

    @Column(unique = true)
    private String invoiceNumber;
    private JobStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobId")
    @Builder.Default
    private List<CustomFields_jobs> customFields = new ArrayList<>();

    @OneToOne
    @JsonBackReference
    private ReplacementNote replacementNote;
}
