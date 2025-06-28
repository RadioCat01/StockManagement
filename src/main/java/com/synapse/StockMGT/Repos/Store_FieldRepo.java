package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Store_Fields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Store_FieldRepo extends JpaRepository<Store_Fields, Integer> {
}
