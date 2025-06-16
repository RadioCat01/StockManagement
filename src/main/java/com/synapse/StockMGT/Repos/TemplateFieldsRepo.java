package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CustomFields.TemplateFields;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateFieldsRepo extends JpaRepository<TemplateFields,Integer> {
    List<TemplateFields> findByTemplate_TemplateId(int id);
}
