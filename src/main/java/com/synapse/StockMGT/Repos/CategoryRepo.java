package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryName(String name);
}
