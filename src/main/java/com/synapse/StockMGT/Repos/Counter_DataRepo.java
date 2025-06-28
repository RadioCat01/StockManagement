package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Counter_Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Counter_DataRepo extends JpaRepository<Counter_Data,Integer> {
}
