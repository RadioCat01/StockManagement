package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByBrandName(String brandName);
    Optional<Brand> findByBrandNameIgnoreCase(String brandName);
}
