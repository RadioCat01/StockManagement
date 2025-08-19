package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.POS_Fields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POS_FieldRepo extends JpaRepository<POS_Fields,Integer> {
}
