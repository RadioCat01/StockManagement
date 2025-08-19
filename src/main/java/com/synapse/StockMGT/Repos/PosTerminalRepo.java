package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.PosTerminal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosTerminalRepo extends JpaRepository<PosTerminal,Integer> {
}
