package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.BulkTransferDTO;
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
    public ResponseEntity<Map<String, Object>> inv(
            @RequestParam(defaultValue = "1") int draw,
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "10") int length,
            @RequestParam(required = false) Integer storeId,
            @RequestParam(name = "order[0][column]", defaultValue = "0") int sortColumn,
            @RequestParam(name = "order[0][dir]", defaultValue = "asc") String sortDirection,
            @RequestParam(name = "search[value]", defaultValue = "") String search) {
        controllerLogger.info("Inventory Controller Called.");
        int pageSize = Math.max(1, Math.min(length, 100));
        int page = Math.max(start, 0) / pageSize;
        var inventory = inventoryService.getInventory(storeId, page, pageSize, search, sortColumn, sortDirection);

        Map<String, Object> response = new HashMap<>();
        response.put("draw", Math.max(draw, 0));
        response.put("recordsTotal", inventoryService.countInventory(storeId));
        response.put("recordsFiltered", inventory.getTotalElements());
        response.put("data", inventory.getContent());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/trf")
    public ResponseEntity<?> trf() {
        controllerLogger.info("Inventory Controller Called.");
        return ResponseEntity.ok(inventoryService.getTransfers());
    }

    @GetMapping("/itemHistory")
    public ResponseEntity<?> itemHistory(@RequestParam String serial) {
        controllerLogger.info("Inventory Controller Called.");
        return ResponseEntity.ok(inventoryService.getItemHistory(serial));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferReqDTO transferReqDTO) {
        controllerLogger.info("Inventory Controller Called.");
        Map<String, String> response = new HashMap<>();
        response.put("Message",inventoryService.transferStock(transferReqDTO));
        return ResponseEntity.ok(response);
    }

    @PostMapping("transferBulk")
    public ResponseEntity<?> transferBulk(@RequestBody BulkTransferDTO bulkTransferDTO) {
        controllerLogger.info("Inventory Controller Called.");
        Map<String, String> response = new HashMap<>();
        response.put("Message",inventoryService.bulkTransfer(bulkTransferDTO));
        return ResponseEntity.ok(response);
    }
}
