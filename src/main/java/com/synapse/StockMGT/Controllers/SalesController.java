package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.SoldDTO;
import com.synapse.StockMGT.Services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;
    Logger salesLogger = Logger.getLogger("SalesController Called.");

    @GetMapping("/getSaleItems")
    public ResponseEntity<?> getItems() {
        salesLogger.info("Sales Controller: getItems");
        return ResponseEntity.ok(salesService.getItems());
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<?> getCustomers() {
        salesLogger.info("Sales Controller: getCustomers");
        return ResponseEntity.ok(salesService.getCustomers());
    }

    @PostMapping("/createSale")
    public ResponseEntity<?> createSale(@RequestBody SoldDTO sale) {
        Map<String, Integer> response = new HashMap<>();
        response.put("invoiceId", salesService.createSale(sale));
        salesLogger.info("Sales Controller: createSale"+response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/salesReport")
    public ResponseEntity<?> getSalesReport() {
        salesLogger.info("Sales Controller: getSalesReport");
        return ResponseEntity.ok(salesService.getSaleReport());
    }
}
