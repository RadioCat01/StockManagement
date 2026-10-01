package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import com.synapse.StockMGT.Services.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
public class FormController {
    private final FormService formService;

    @GetMapping("/company")
    public ResponseEntity<?> getCompanyForms() {
        return ResponseEntity.ok(formService.getCompanyFields());
    }

    @PostMapping("/company")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addCompanyFields(@RequestBody FieldDTO field){
        return ResponseEntity.ok(formService.addCompanyField(field));
    }

    @GetMapping("/subcompany")
    public ResponseEntity<?> getSubCompanyForms() {
        return ResponseEntity.ok(formService.getSubCompanyFields());
    }

    @PostMapping("/subcompany")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addSubCompanyFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addSubCompanyField(field));
    }

    @GetMapping("/store")
    public ResponseEntity<?> getStoreForms() {
        return ResponseEntity.ok(formService.getStoreFields());
    }

    @PostMapping("/store")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addStoreFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addStoreField(field));
    }

    @GetMapping("/storefront")
    public ResponseEntity<?> getStorefrontForms() {
        return ResponseEntity.ok(formService.getStorefrontFields());
    }

    @PostMapping("/storefront")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addStorefrontFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addStorefrontField(field));
    }

    @GetMapping("/counter")
    public ResponseEntity<?> getCounterForms() {
        return ResponseEntity.ok(formService.getCounterFields());
    }

    @PostMapping("/counter")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addCounterFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addCounterField(field));
    }

    @GetMapping("/scanner")
    public ResponseEntity<?> getScannerForms() {
        return ResponseEntity.ok(formService.getScannerFields());
    }

    @PostMapping("/scanner")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addScannerFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addScannerField(field));
    }

    @GetMapping("/posterminal")
    public ResponseEntity<?> getPOSTerminalForms() {
        return ResponseEntity.ok(formService.getPOSTerminalFields());
    }

    @PostMapping("/posterminal")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addPOSTerminalFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addPOSTerminalField(field));
    }

    @GetMapping("/drawer")
    public ResponseEntity<?> getCashDrawerForms() {
        return ResponseEntity.ok(formService.getCashDrawerFields());
    }

    @PostMapping("/drawer")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addCashDrawerFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addCashDrawerField(field));
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomerForms() {
        return ResponseEntity.ok(formService.getCustomerFields());
    }

    @PostMapping("/customer")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addCustomerFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addCustomerField(field));
    }

    @GetMapping("/supplier")
    public ResponseEntity<?> getSupplierForms() {
        return ResponseEntity.ok(formService.getSupplierFields());
    }

    @PostMapping("/supplier")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addSupplierFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addSupplierField(field));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategoryForms() {
        return ResponseEntity.ok(formService.getCategoryFields());
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addCategoryFields(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addCategoryField(field));
    }

    @GetMapping("/brand")
    public ResponseEntity<?> getBrandForms() {
        return ResponseEntity.ok(formService.getBrandFields());
    }

    @PostMapping("/brand")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addBrandField(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addBrandField(field));
    }

    @GetMapping("/iteminfo")
    public ResponseEntity<?> getItemInfoForms() {
        return ResponseEntity.ok(formService.getItemInfoFields());
    }

    @PostMapping("/iteminfo")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addItemInfoField(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addItemInfoField(field));
    }

    @GetMapping("/item")
    public ResponseEntity<?> getItemForms() {
        return ResponseEntity.ok(formService.getItemFields());
    }

    @PostMapping("/item")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addItemField(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addItemField(field));
    }

    @GetMapping("/jobnotes")
    public ResponseEntity<?> getJobNotesForms() {
        return ResponseEntity.ok(formService.getJobNotesFields());
    }

    @PostMapping("/jobnotes")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> addJobNotesField(@RequestBody FieldDTO field) {
        return ResponseEntity.ok(formService.addJobNotesField(field));
    }


}
