package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.JobNotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobNoteRepo extends JpaRepository<JobNotes, Integer> {
}
