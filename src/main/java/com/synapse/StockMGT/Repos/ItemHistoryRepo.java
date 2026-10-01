package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.ItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemHistoryRepo extends JpaRepository<ItemHistory, Integer> {
    List<ItemHistory> findBySerialNo(String serialNo);
    List<ItemHistory> findAllBySerialNoAndCompany_CompanyId(String serialNo, Integer companyId);
}
