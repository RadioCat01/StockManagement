package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.SupplierGRN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierGRNRepo  extends JpaRepository<SupplierGRN, Integer> {
    List<SupplierGRN> findBySupplierId(int supplierId);
    List<SupplierGRN> findBySupplierIdAndCompany_CompanyId(int supplierId, Integer companyId);
    List<SupplierGRN> findByItemCode(String name);
}
