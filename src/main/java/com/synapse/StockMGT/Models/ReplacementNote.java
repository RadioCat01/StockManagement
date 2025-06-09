package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReplacementNote")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ReplacementNote extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replacementNoteId;

    private String repNumber;
    private LocalDate replacementDate;

    @OneToOne
    @JoinColumn(name = "jobNotesId")
    @JsonManagedReference
    private JobNotes jobNotes;
}
