package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.BrandDTO;
import com.synapse.StockMGT.DTOs.ItemDTO;
import com.synapse.StockMGT.Repos.CategoryRepo;
import com.synapse.StockMGT.Services.InventoryService;
import com.synapse.StockMGT.Services.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/mgt")
@RequiredArgsConstructor
public class ManagementController {
    private final ManagementService managementService;
    private final CategoryRepo categoryRepo;
    Logger controllerLogger = Logger.getLogger("AUDIT");

    @GetMapping("/flatCat")
    public ResponseEntity<?> flatCat() {
        controllerLogger.info("Management Controller Called.");
        return ResponseEntity.ok(managementService.getFlatCat());
    }

    @GetMapping("/cat")
    public ResponseEntity<?> cat() {
        controllerLogger.info("Management Controller Called.");
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    @GetMapping("/stores")
    public ResponseEntity<?> stores() {
        controllerLogger.info("Store Controller Called.");
        return ResponseEntity.ok(managementService.getStores());
    }

    @PostMapping("/createCategory")
    public ResponseEntity<?> createItem(@RequestBody String categoryName) {
        Map<String, String> response = new HashMap<>();
        response.put("Message", managementService.createCategory(categoryName));
        controllerLogger.info("Management Controller Called."+response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createBrand")
    public ResponseEntity<?> createBrand(@RequestBody BrandDTO brandDTO) {
        Map<String, String> response = new HashMap<>();
        response.put("Message", managementService.createBrand(brandDTO));
        controllerLogger.info("Management Controller Called."+response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createItem")
    public ResponseEntity<?> createItem(@RequestBody ItemDTO itemDTO) {
        Map<String, String> response = new HashMap<>();
        response.put("Message", managementService.createItem(itemDTO));
        controllerLogger.info("Management Controller Called."+response);
        return ResponseEntity.ok(response);
    }
}
