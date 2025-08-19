package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Company_Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Data_CompanyRepo extends JpaRepository<Company_Data,Long> {
}
