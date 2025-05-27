package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.InventoryDTO;
import com.synapse.StockMGT.DTOs.TransferReqDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Repos.CategoryRepo;
import com.synapse.StockMGT.Repos.ItemRepo;
import com.synapse.StockMGT.Repos.StoreRepo;
import com.synapse.StockMGT.Repos.TransferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private static final Random random = new Random();

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
        String description = null;
        for (InventoryDTO inventoryDTO : transferReqDTO.getItems()) {
            if(inventoryDTO.getStoreId() != store.getStoreId()) {
                Item item = itemRepo.findById(inventoryDTO.getItemId())
                        .orElseThrow(() -> new RuntimeException("Item not found"));
                prevStore = item.getStore();
                item.setStore(store);
                description = inventoryDTO.getDescription();
                item.setLastUpdate(LocalDate.now());
                item.setCurrentPosition(store.getSubCompany().getSubCompanyName());
                itemRepo.save(item);
                sb.append(inventoryDTO.getBrand()).append(inventoryDTO.getItemCode())
                        .append(" - ").append(item.getSerialNumber())
                        .append("<br><br>");
            }
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
    public static String generateTransferNumber() {
        int number = random.nextInt(1_000_000);
        return String.format("%06d", number);
    }
}
