package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepo extends JpaRepository<Sales,Integer> {
    java.util.List<Sales> findAllByCompany_CompanyId(Integer companyId);
}
