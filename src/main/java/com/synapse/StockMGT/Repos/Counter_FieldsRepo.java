package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Counter_Fields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Counter_FieldsRepo extends JpaRepository<Counter_Fields, Integer> {
}
