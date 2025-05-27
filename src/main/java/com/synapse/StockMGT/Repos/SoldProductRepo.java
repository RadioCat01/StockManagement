package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.SoldProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldProductRepo extends JpaRepository<SoldProducts, Integer> {
}
