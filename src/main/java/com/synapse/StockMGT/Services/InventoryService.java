package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.BulkTransferDTO;
import com.synapse.StockMGT.DTOs.InventoryDTO;
import com.synapse.StockMGT.DTOs.ItemHistoryResDTO;
import com.synapse.StockMGT.DTOs.TransferReqDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final String[] INVENTORY_SORT_EXPRESSIONS = {
            "coalesce(c.categoryName, g.categoryName)",
            "coalesce(g.itemCode, info.itemCode)",
            "coalesce(g.productDescription, info.itemDescription)",
            "i.serialNumber",
            "g.grnDate",
            "i.lastUpdate",
            "i.stockType"
    };

    private final TransferRepo transferRepo;
    private final ItemRepo itemRepo;
    private final StoreRepo storeRepo;
    private final ItemHistoryRepo itemHistoryRepo;
    private final ItemInfoRepo itemInfoRepo;
    private static final Random random = new Random();

    @Transactional(readOnly = true)
    public Page<InventoryDTO> getInventory(
            Integer storeId, int page, int size, String search, int sortColumn, String sortDirection) {
        String sortExpression = INVENTORY_SORT_EXPRESSIONS[
                Math.max(0, Math.min(sortColumn, INVENTORY_SORT_EXPRESSIONS.length - 1))];
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return itemRepo.findInventory(
                storeId,
                search.trim(),
                PageRequest.of(page, size, JpaSort.unsafe(direction, sortExpression)));
    }

    @Transactional(readOnly = true)
    public long countInventory(Integer storeId) {
        return itemRepo.countInventoryByStoreId(storeId);
    }

    public List<Transfers> getTransfers() {
        return transferRepo.findAll();
    }

    @Transactional
    public String transferStock(TransferReqDTO transferReqDTO) {
        Store store = storeRepo.findById(transferReqDTO.getTransferTo())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        StringBuilder sb = new StringBuilder();
        Store prevStore = null;
        List<ItemHistory> histories = new ArrayList<>();
        List<Item> itemsToSave = new ArrayList<>();
        String transferredToState = "Transferred to: " + store.getSubCompany().getSubCompanyName();
        LocalDate now = LocalDate.now();

        for (InventoryDTO inventoryDTO : transferReqDTO.getItems()) {
            if(inventoryDTO.getStoreId() != store.getStoreId()) {
                Item item = itemRepo.findById(inventoryDTO.getItemId())
                        .orElseThrow(() -> new RuntimeException("Item not found"));
                prevStore = item.getStore();
                histories.add(ItemHistory.builder()
                                .brand(inventoryDTO.getBrand())
                                .itemCode(inventoryDTO.getItemCode())
                                .serialNo(item.getSerialNumber())
                                .currentState(transferredToState)
                                .lastUpdate(now)
                                .build());

                item.setStore(store);
                item.setLastUpdate(now);
                item.setCurrentPosition(store.getSubCompany().getSubCompanyName());
                itemsToSave.add(item);
                sb.append(inventoryDTO.getBrand()).append(" ").append(inventoryDTO.getItemCode())
                        .append(" - ").append(item.getSerialNumber())
                        .append("<br><br>");
            }
        }
        if (!histories.isEmpty()) {
            itemHistoryRepo.saveAll(histories);
        }
        if (!itemsToSave.isEmpty()) {
            itemRepo.saveAll(itemsToSave);
        }
        assert prevStore != null;
        transferRepo.save(Transfers.builder()
                        .transferNumber(generateTransferNumber())
                        .transferDate(LocalDate.now())
                        .reason(transferReqDTO.getReason())
                        .serials(sb.toString())
                        .transferFrom(prevStore.getSubCompany().getSubCompanyName())
                        .transferTo(store.getSubCompany().getSubCompanyName())
                        .build());
        return "Done";
    }

    @Transactional
    public String bulkTransfer(BulkTransferDTO bulkTransferDTO) {
        Store store = storeRepo.findById(bulkTransferDTO.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        ItemInfo itemInfo =itemInfoRepo.findByItemCode(bulkTransferDTO.getItemCode())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        List<Item> items = itemInfo.getItems().stream()
                .filter(item -> item.getStore().getStoreId() != bulkTransferDTO.getStoreId())
                .limit(bulkTransferDTO.getItemQuantity())
                .toList();

        StringBuilder sb = new StringBuilder();
        Store prevStore = null;
        List<ItemHistory> histories = new ArrayList<>();
        List<Item> itemsToSave = new ArrayList<>();
        String transferredToState = "Transferred to: " + store.getSubCompany().getSubCompanyName();
        LocalDate now = LocalDate.now();

        for (Item item : items) {
            prevStore = item.getStore();
            item.setLastUpdate(now);
            item.setCurrentPosition(store.getSubCompany().getSubCompanyName());
            item.setStore(store);
            itemsToSave.add(item);
            histories.add(ItemHistory.builder()
                            .brand(bulkTransferDTO.getBrand())
                            .itemCode(bulkTransferDTO.getItemCode())
                            .serialNo(item.getSerialNumber())
                            .currentState(transferredToState)
                            .lastUpdate(now)
                            .build());
            sb.append(bulkTransferDTO.getBrand()).append(" ").append(bulkTransferDTO.getItemCode())
                    .append(" - ").append(item.getSerialNumber())
                    .append("<br><br>");
        }
        if (!histories.isEmpty()) {
            itemHistoryRepo.saveAll(histories);
        }
        if (!itemsToSave.isEmpty()) {
            itemRepo.saveAll(itemsToSave);
        }
        assert prevStore != null;

        transferRepo.save(Transfers.builder()
                .transferNumber(generateTransferNumber())
                .transferDate(LocalDate.now())
                .reason(bulkTransferDTO.getReason())
                .serials(sb.toString())
                .transferFrom(prevStore.getSubCompany().getSubCompanyName())
                .transferTo(store.getSubCompany().getSubCompanyName())
                .build());
        return "Done";
    }

    public static String generateTransferNumber() {
        int number = random.nextInt(1_000_000);
        return String.format("%06d", number);
    }

    public List<ItemHistoryResDTO> getItemHistory(String serialNumber) {
        return itemHistoryRepo.findBySerialNo(serialNumber).stream().map(i -> ItemHistoryResDTO.builder()
                .brand(i.getBrand())
                .itemCode(i.getItemCode())
                .serialNo(i.getSerialNo())
                .lastUpdate(i.getLastUpdate())
                .currentState(i.getCurrentState())
                .build()).toList();
    }
}
