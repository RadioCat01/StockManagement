package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.JobItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobItemRepo extends JpaRepository<JobItem, Long> {
}
