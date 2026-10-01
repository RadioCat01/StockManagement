package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.DTOs.InventoryDTO;
import com.synapse.StockMGT.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    @Query("select new com.synapse.StockMGT.DTOs.InventoryDTO(i.itemId, coalesce(b.brandName, g.brandName), " +
            "coalesce(g.itemCode, info.itemCode), coalesce(g.productDescription, info.itemDescription), " +
            "i.serialNumber, coalesce(c.categoryName, g.categoryName), s.storeId, g.grnDate, i.lastUpdate, " +
            "i.currentPosition, i.stockType) " +
            "from Item i join i.store s left join i.supplierGRN g left join i.itemInfo info " +
            "left join info.brand b left join b.category c " +
            "where (:storeId is null or s.storeId = :storeId) " +
            "and (:companyId is null or s.company.companyId = :companyId) " +
            "and (:storeFrontId is null or exists (select sf.storefrontId from StoreFront sf join sf.store sfStore " +
            "where sf.storefrontId = :storeFrontId and sfStore.storeId = s.storeId)) " +
            "and (:search = '' or lower(coalesce(b.brandName, g.brandName, '')) like lower(concat('%', :search, '%')) " +
            "or lower(coalesce(g.itemCode, info.itemCode, '')) like lower(concat('%', :search, '%')) " +
            "or lower(coalesce(g.productDescription, info.itemDescription, '')) like lower(concat('%', :search, '%')) " +
            "or lower(i.serialNumber) like lower(concat('%', :search, '%')) " +
            "or lower(coalesce(c.categoryName, g.categoryName, '')) like lower(concat('%', :search, '%')) " +
            "or lower(coalesce(i.stockType, '')) like lower(concat('%', :search, '%')))")
    Page<InventoryDTO> findInventory(
            @Param("storeId") Integer storeId,
            @Param("companyId") Integer companyId,
            @Param("storeFrontId") Integer storeFrontId,
            @Param("search") String search,
            Pageable pageable);

    @Query("select count(i) from Item i where (:storeId is null or i.store.storeId = :storeId) " +
            "and (:companyId is null or i.store.company.companyId = :companyId) " +
            "and (:storeFrontId is null or exists (select sf.storefrontId from StoreFront sf join sf.store sfStore " +
            "where sf.storefrontId = :storeFrontId and sfStore.storeId = i.store.storeId))")
    long countInventoryByStoreId(
            @Param("storeId") Integer storeId,
            @Param("companyId") Integer companyId,
            @Param("storeFrontId") Integer storeFrontId);

    Optional<Item> findBySerialNumber(String serialNumber);
    void deleteBySerialNumber(String serialNumber);

    @Modifying
    @Query("DELETE FROM Item i WHERE i.itemId = :itemId")
    void deleteByItemId(@Param("itemId") Integer itemId);
}
