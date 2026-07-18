package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.BulkTransferDTO;
import com.synapse.StockMGT.DTOs.InventoryDTO;
import com.synapse.StockMGT.DTOs.ItemHistoryResDTO;
import com.synapse.StockMGT.DTOs.TransferReqDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final CategoryRepo categoryRepo;
    private final TransferRepo transferRepo;
    private final ItemRepo itemRepo;
    private final StoreRepo storeRepo;
    private final ItemHistoryRepo itemHistoryRepo;
    private final ItemInfoRepo itemInfoRepo;
    private static final Random random = new Random();

    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventory() {
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();
        List<Category> existingCategories = categoryRepo.findAll();

        for (Category category : existingCategories) {
            for (Brand brand : category.getBrands()) {
                for (SupplierGRN grn: brand.getSupplierGRNs()){
                    for(Item item: grn.getItems()){
                        inventoryDTOList.add(InventoryDTO.builder()
                                        .itemId(item.getItemId())
                                        .brand(brand.getBrandName())
                                        .itemCode(grn.getItemCode())
                                        .description(grn.getProductDescription())
                                        .productSerial(item.getSerialNumber())
                                        .category(category.getCategoryName())
                                        .grnDate(grn.getGrnDate())
                                        .storeId(item.getStore().getStoreId())
                                        .grnDate(grn.getGrnDate())
                                        .lastUpdate(item.getLastUpdate())
                                        .currentPosition(item.getCurrentPosition())
                                        .stockType(item.getStockType())
                                        .build());
                    }
                }
            }
        }
        return inventoryDTOList;
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
