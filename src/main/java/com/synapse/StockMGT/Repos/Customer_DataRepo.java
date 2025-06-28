package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Customer_Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Customer_DataRepo extends JpaRepository<Customer_Data, Integer> {
}
