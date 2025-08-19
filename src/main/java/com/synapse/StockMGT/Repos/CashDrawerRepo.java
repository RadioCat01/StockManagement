package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.CashDrawer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashDrawerRepo extends JpaRepository<CashDrawer,Integer> {
}
