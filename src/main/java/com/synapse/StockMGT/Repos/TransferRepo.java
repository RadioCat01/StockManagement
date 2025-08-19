package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<Transfers, Integer> {
}
