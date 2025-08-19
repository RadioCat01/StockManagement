package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Item_Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Item_DataRepo extends JpaRepository<Item_Data, Integer> {
}
