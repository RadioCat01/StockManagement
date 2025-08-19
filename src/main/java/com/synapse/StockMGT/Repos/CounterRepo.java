package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.Counter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CounterRepo  extends JpaRepository<Counter,Integer> {
}
