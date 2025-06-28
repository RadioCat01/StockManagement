package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.CustomFields.Company_Fields;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Company_FieldsRepo extends JpaRepository<Company_Fields, Integer> {
    List<Company_Fields> findAllByCompany_CompanyId(Integer companyId);
}
