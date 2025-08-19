package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.StoreFront_Fields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFront_FieldRepo extends JpaRepository<StoreFront_Fields,Integer> {
}
