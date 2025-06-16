package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CustomFields.Templates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepo extends JpaRepository<Templates,Integer> {
    Optional<Templates> findByTemplateType(String templateType);
}
