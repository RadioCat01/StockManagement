package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    Optional<Item> findBySerialNumber(String serialNumber);
    void deleteBySerialNumber(String serialNumber);

    @Modifying
    @Query("DELETE FROM Item i WHERE i.itemId = :itemId")
    void deleteByItemId(@Param("itemId") Integer itemId);
}
