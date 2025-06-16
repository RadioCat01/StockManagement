package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFrontRepo extends JpaRepository<StoreFront,Integer> {
}
