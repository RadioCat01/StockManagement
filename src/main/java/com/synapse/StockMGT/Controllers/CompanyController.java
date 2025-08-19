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

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createCompany(dto.getFormData()));
    }

    @PostMapping("/subcompany")
    public ResponseEntity<?> createSubCompany(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createSubCompany(dto.getFormData()));
    }

    @PostMapping("/store")
    public ResponseEntity<?> createStore(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createStore(dto.getFormData()));
    }

    @PostMapping("/storefront")
    public ResponseEntity<?> createStorefront(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createStoreFront(dto.getFormData()));
    }

    @PostMapping("/counter")
    public ResponseEntity<?> createCounter(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createCounter(dto.getFormData()));
    }

    @PostMapping("/scanner")
    public ResponseEntity<?> createScanner(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createScanner(dto.getFormData()));
    }

    @PostMapping("/posterminal")
    public ResponseEntity<?> createPos(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createPOSTerminal(dto.getFormData()));
    }

    @PostMapping("/drawer")
    public ResponseEntity<?> createDrawer(@RequestBody DataReqDTO dto) {
        return ResponseEntity.ok(companyService.createDrawer(dto.getFormData()));
    }


    @GetMapping("/company")
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/subcompany")
    public ResponseEntity<?> getAllSubCompanies() {
        return ResponseEntity.ok(companyService.getAllSubCompanies());
    }

    @GetMapping("/store")
    public ResponseEntity<?> getAllStores() {
        return ResponseEntity.ok(companyService.getAllStores());
    }

    @GetMapping("/storeFront")
    public ResponseEntity<?> getAllStoreFronts() {
        return ResponseEntity.ok(companyService.getAllStoreFronts());
    }

    @GetMapping("/counter")
    public ResponseEntity<?> getAllCounters() {
        return ResponseEntity.ok(companyService.getAllCounters());
    }

    @GetMapping("/scanner")
    public ResponseEntity<?> getAllScanners() {
        return ResponseEntity.ok(companyService.getAllScanners());
    }

    @GetMapping("/posterminal")
    public ResponseEntity<?> getAllPOSTerminals() {
        return ResponseEntity.ok(companyService.getAllPOSTerminals());
    }

    @GetMapping("/drawer")
    public ResponseEntity<?> getAllDrawers() {
        return ResponseEntity.ok(companyService.getAllDrawers());
    }
}
