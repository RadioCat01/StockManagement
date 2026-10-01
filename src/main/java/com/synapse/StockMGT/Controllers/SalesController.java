package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.SoldDTO;
import com.synapse.StockMGT.Services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'CASHIER')")
    public ResponseEntity<?> getItems() {
        salesLogger.info("Sales Controller: getItems");
        return ResponseEntity.ok(salesService.getItems());
    }

    @GetMapping("/getCustomers")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'CASHIER')")
    public ResponseEntity<?> getCustomers() {
        salesLogger.info("Sales Controller: getCustomers");
        return ResponseEntity.ok(salesService.getCustomers());
    }

    @PostMapping("/createSale")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'CASHIER')")
    public ResponseEntity<?> createSale(@RequestBody SoldDTO sale) {
        Map<String, Integer> response = new HashMap<>();
        response.put("invoiceId", salesService.createSale(sale));
        salesLogger.info("Sales Controller: createSale"+response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/salesReport")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<?> getSalesReport() {
        salesLogger.info("Sales Controller: getSalesReport");
        return ResponseEntity.ok(salesService.getSaleReport());
    }
}
