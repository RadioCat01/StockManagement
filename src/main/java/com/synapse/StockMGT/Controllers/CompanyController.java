package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.CompanyDTOs.*;
import com.synapse.StockMGT.DTOs.FormDTOs.DataReqDTO;
import com.synapse.StockMGT.Services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/com")
    public ResponseEntity<?> createCompany(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createCompany(dto.getFormData()));
    }

    @PostMapping("/sub")
    public ResponseEntity<?> createSubCompany(@RequestBody SubCompanyDTO subCompanyDTO) {
        return ResponseEntity.ok(companyService.createSubCompany(subCompanyDTO));
    }

    @PostMapping("/store")
    public ResponseEntity<?> createStore(@RequestBody StoreDTO storeDTO) {
        return ResponseEntity.ok(companyService.createStore(storeDTO));
    }

    @PostMapping("/storeFront")
    public ResponseEntity<?> createStorefront(@RequestBody StoreFrontDTO storeFrontDTO) {
        return ResponseEntity.ok(companyService.createStoreFront(storeFrontDTO));
    }

    @PostMapping("/counter")
    public ResponseEntity<?> createCounter(@RequestBody CounterDTO counterDTO) {
        return ResponseEntity.ok(companyService.createCounter(counterDTO));
    }

    @PostMapping("/scanner")
    public ResponseEntity<?> createScanner(@RequestBody ScannerDTO scannerDTO) {
        return ResponseEntity.ok(companyService.createScanner(scannerDTO));
    }

    @PostMapping("/pos")
    public ResponseEntity<?> createPos(@RequestBody POSTerminalDTO posDTO) {
        return ResponseEntity.ok(companyService.createPOSTerminal(posDTO));
    }

    @PostMapping("/drawer")
    public ResponseEntity<?> createDrawer(@RequestBody DrawerDTO drawerDTO) {
        return ResponseEntity.ok(companyService.createDrawer(drawerDTO));
    }


    @GetMapping("/com")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/sub")
    public ResponseEntity<List<SubCompanyDTO>> getAllSubCompanies() {
        return ResponseEntity.ok(companyService.getAllSubCompanies());
    }

    @GetMapping("/store")
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        return ResponseEntity.ok(companyService.getAllStores());
    }

    @GetMapping("/storeFront")
    public ResponseEntity<List<StoreFrontDTO>> getAllStoreFronts() {
        return ResponseEntity.ok(companyService.getAllStoreFronts());
    }

    @GetMapping("/counter")
    public ResponseEntity<List<CounterDTO>> getAllCounters() {
        return ResponseEntity.ok(companyService.getAllCounters());
    }

    @GetMapping("/scanner")
    public ResponseEntity<List<ScannerDTO>> getAllScanners() {
        return ResponseEntity.ok(companyService.getAllScanners());
    }

    @GetMapping("/pos")
    public ResponseEntity<List<POSTerminalDTO>> getAllPOSTerminals() {
        return ResponseEntity.ok(companyService.getAllPOSTerminals());
    }

    @GetMapping("/drawer")
    public ResponseEntity<List<DrawerDTO>> getAllDrawers() {
        return ResponseEntity.ok(companyService.getAllDrawers());
    }
}
