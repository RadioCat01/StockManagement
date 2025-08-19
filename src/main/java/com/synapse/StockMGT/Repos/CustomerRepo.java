package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customers, Integer> {
    Optional<Customers> findByPhone(String username);
}
