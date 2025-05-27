package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.InventoryDTO;
import com.synapse.StockMGT.DTOs.TransferReqDTO;
import com.synapse.StockMGT.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("inventoryCont")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    Logger controllerLogger = Logger.getLogger("AUDIT");

    @GetMapping("/inv")
    public ResponseEntity<?> inv() {
        controllerLogger.info("Inventory Controller Called.");
        return ResponseEntity.ok(inventoryService.getInventory());
    }
    @GetMapping("/trf")
    public ResponseEntity<?> trf() {
        controllerLogger.info("Inventory Controller Called.");
        return ResponseEntity.ok(inventoryService.getTransfers());
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferReqDTO transferReqDTO) {
        controllerLogger.info("Inventory Controller Called.");
        Map<String, String> response = new HashMap<>();
        response.put("Message",inventoryService.transferStock(transferReqDTO));
        return ResponseEntity.ok(response);
    }
}
