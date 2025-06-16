package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.Scanner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScannerRepo extends JpaRepository<Scanner,Integer> {
}
