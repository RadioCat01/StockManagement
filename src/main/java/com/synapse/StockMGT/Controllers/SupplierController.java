package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.SupplierReqDTO;
import com.synapse.StockMGT.Models.Supplier;
import com.synapse.StockMGT.Services.ManagementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/supplier")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
public class SupplierController {
    private final ManagementService managementService;
    Logger supplierLogger = LoggerFactory.getLogger("AUDIT");

    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        supplierLogger.info("Supplier Controller: getAllSuppliers");
        return ResponseEntity.ok(managementService.getSupplierRES());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierReqDTO supplier) {
        supplierLogger.info("Supplier Controller: createSupplier");
        return ResponseEntity.ok(managementService.saveSupplier(supplier));
    }

}
