package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.SubCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubComRepo extends JpaRepository<SubCompany, Integer> {
    Optional<SubCompany> findBySubCompanyName(String subComName);
}
