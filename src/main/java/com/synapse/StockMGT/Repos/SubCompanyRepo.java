package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCompanyRepo extends JpaRepository<SubCompany,Integer> {
}
