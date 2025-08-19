package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
}
