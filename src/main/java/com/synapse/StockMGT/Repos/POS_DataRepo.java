package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.POS_Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POS_DataRepo extends JpaRepository<POS_Data, Integer> {
}
