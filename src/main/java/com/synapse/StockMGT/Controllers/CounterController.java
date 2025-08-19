package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class CounterController {
    private final StoreFrontRepo  storeFrontRepo;
    private final CounterRepo counterRepo;
    private final ScannerRepo scannerRepo;
    private final PosTerminalRepo posTerminalRepo;
    private final CashDrawerRepo cashDrawerRepo;
    private final StoreRepo storeRepo;

    @GetMapping("/store")
    public ResponseEntity<?> getStore(){
        return ResponseEntity.ok(storeRepo.findAll());
    }
    @GetMapping("/storeFront")
    public ResponseEntity<?> getStoreFront(){
        return ResponseEntity.ok(storeFrontRepo.findAll());
    }
    @GetMapping("/counter")
    public ResponseEntity<?> getCounter(){
        return ResponseEntity.ok(counterRepo.findAll());
    }
    @GetMapping("/scanner")
    public ResponseEntity<?> getScanner(){
        return ResponseEntity.ok(scannerRepo.findAll());
    }
    @GetMapping("/posTerminal")
    public ResponseEntity<?> getPosTerminal(){
        return ResponseEntity.ok(posTerminalRepo.findAll());
    }
    @GetMapping("/drawer")
    public ResponseEntity<?> getDrawer(){
        return ResponseEntity.ok(cashDrawerRepo.findAll());
    }
}
