package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.InvoiceDTO;
import com.synapse.StockMGT.Models.Invoice;
import com.synapse.StockMGT.Services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/invoiceCont")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    Logger invoiceLogger = Logger.getLogger("AUDIT");

    @GetMapping("/invoice/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable Integer id) {
        invoiceLogger.info("Sales Controller: getInvoice");
        return ResponseEntity.ok(invoiceService.getInvoices(id));
    }

    @GetMapping("/invoice")
    public ResponseEntity<?> getInvoices() {
        invoiceLogger.info("Sales Controller: getInvoices");
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/invoiceByNumber")
    public ResponseEntity<?> getInvoiceByNumber(@RequestParam String number) {
        invoiceLogger.info("Sales Controller: getInvoiceByNumber");
        return ResponseEntity.ok(invoiceService.getInvoiceByNumber(number));
    }

    @PostMapping("/updateInvoice")
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoice) {
        Map<String, String> response = new HashMap<>();
        response.put("Message",invoiceService.updateInvoice(invoice));
        return ResponseEntity.ok(response);
    }
}
