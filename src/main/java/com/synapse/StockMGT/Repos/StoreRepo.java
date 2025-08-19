package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Integer> {
    Optional<Store> findByStoreName(String name);
}
