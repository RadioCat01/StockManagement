package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company,Integer> {
    Optional<Company> findByCompanyName(String name);
}
