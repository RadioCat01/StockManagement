package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.Services.SupplierGRNService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplierGRNS")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
public class SupplierGRNController {
    private final SupplierGRNService supplierGRNService;
    Logger grnsLogger = LoggerFactory.getLogger("AUDIT");

    @GetMapping("/getSGRNs")
    public ResponseEntity<?> getAllGRNsForSupplier(@RequestParam Integer supplierId) {
        grnsLogger.info("Supplier GRN Called for Supplier ID: " + supplierId);
        return supplierGRNService.getAllGRNsForSupplier(supplierId);
    }
}
