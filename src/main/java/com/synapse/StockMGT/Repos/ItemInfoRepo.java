package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Brand;
import com.synapse.StockMGT.Models.ItemInfo;
import com.synapse.StockMGT.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemInfoRepo extends JpaRepository<ItemInfo, Integer> {
    Optional<ItemInfo> findByItemCode(String code);
    Optional<ItemInfo> findByItemCodeAndItems_Supplier_SupplierId(String itemCode, Integer supplierId);
    List<ItemInfo> findByBrand(Brand brand);
}
