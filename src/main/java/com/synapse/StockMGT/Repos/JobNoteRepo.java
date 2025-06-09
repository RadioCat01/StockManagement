package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.JobNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobNoteRepo extends JpaRepository<JobNotes, Integer> {
    Optional<JobNotes> findByJobNumber(String number);
}
