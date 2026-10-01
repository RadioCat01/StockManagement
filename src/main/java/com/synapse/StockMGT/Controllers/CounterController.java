package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.*;
import com.synapse.StockMGT.User.AccessScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
public class CounterController {
    private final StoreFrontRepo  storeFrontRepo;
    private final CounterRepo counterRepo;
    private final ScannerRepo scannerRepo;
    private final PosTerminalRepo posTerminalRepo;
    private final CashDrawerRepo cashDrawerRepo;
    private final StoreRepo storeRepo;
    private final AccessScopeService accessScope;

    @GetMapping("/store")
    public ResponseEntity<?> getStore(){
        return ResponseEntity.ok(storeRepo.findAll().stream()
                .filter(store -> store.getCompany() != null
                        && accessScope.isCompanyVisible(store.getCompany().getCompanyId()))
                .toList());
    }
    @GetMapping("/storeFront")
    public ResponseEntity<?> getStoreFront(){
        return ResponseEntity.ok(storeFrontRepo.findAll().stream()
                .filter(front -> front.getCompany() != null
                        && accessScope.isCompanyVisible(front.getCompany().getCompanyId()))
                .toList());
    }
    @GetMapping("/counter")
    public ResponseEntity<?> getCounter(){
        return ResponseEntity.ok(counterRepo.findAll().stream()
                .filter(counter -> counter.getCompany() != null
                        && accessScope.isCompanyVisible(counter.getCompany().getCompanyId()))
                .toList());
    }
    @GetMapping("/scanner")
    public ResponseEntity<?> getScanner(){
        return ResponseEntity.ok(scannerRepo.findAll().stream()
                .filter(scanner -> scanner.getCompany() != null
                        && accessScope.isCompanyVisible(scanner.getCompany().getCompanyId()))
                .toList());
    }
    @GetMapping("/posTerminal")
    public ResponseEntity<?> getPosTerminal(){
        return ResponseEntity.ok(posTerminalRepo.findAll().stream()
                .filter(terminal -> terminal.getCompany() != null
                        && accessScope.isCompanyVisible(terminal.getCompany().getCompanyId()))
                .toList());
    }
    @GetMapping("/drawer")
    public ResponseEntity<?> getDrawer(){
        return ResponseEntity.ok(cashDrawerRepo.findAll().stream()
                .filter(drawer -> drawer.getCompany() != null
                        && accessScope.isCompanyVisible(drawer.getCompany().getCompanyId()))
                .toList());
    }
}
