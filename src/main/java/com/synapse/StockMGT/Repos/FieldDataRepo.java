package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.CustomFields.FieldData;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldDataRepo extends JpaRepository<FieldData, Long> {
    List<FieldData> findByTemplate(Templates template);
}
