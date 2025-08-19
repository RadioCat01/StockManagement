package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Services, Integer> {
}
