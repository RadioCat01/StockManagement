package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.InvoiceDTO;
import com.synapse.StockMGT.Services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UiController {

    Logger logger = LoggerFactory.getLogger("AUDIT");
    private final InvoiceService invoiceService;

    @GetMapping("/inventory")
    public String inventory() {
        logger.info("inventory Page Called.");
        return "inventory";
    }

    @GetMapping("/login")
    public String login() {
        logger.info("login Page Called.");
        return "Login";
    }

    @GetMapping("/grnSummery")
    public String grnSummery() {
        logger.info("grnSummery Page Called.");
        return "GRNSummery";
    }

    @GetMapping("/pos")
    public String pos() {
        logger.info("pos Page Called.");
        return "POS";
    }

    @GetMapping("/category")
    public String addCategory() {
        logger.info("addCategory Page Called.");
        return "Category";
    }

    @GetMapping("/brand")
    public String addBrand() {
        logger.info("addBrandPage Called.");
        return "Brand";
    }

    @GetMapping("/items")
    public String items() {
        logger.info("items Page Called.");
        return "Items";
    }

    @GetMapping("/suppliers")
    public String supplier() {
        logger.info("supplier Page Called.");
        return "Supplier";
    }

    @GetMapping("/sales")
    public String sales() {
        logger.info("sales Page Called.");
        return "Sales";
    }

    @GetMapping("/invoice")
    public String invoice() {
        logger.info("invoice Page Called.");
        return "Invoice";
    }

    @GetMapping("/customerJobs")
    public String customerJobs() {
        logger.info("customerJobs Page Called.");
        return "Job";
    }

    @GetMapping("/company")
    public String company() {
        logger.info("company Page Called.");
        return "Company";
    }

    @GetMapping("/subcompany")
    public String subCompany() {
        logger.info("subCompany Page Called.");
        return "SubCompany";
    }
    @GetMapping("/store")
    public String Store() {
        logger.info("store Page Called.");
        return "Store";
    }
    @GetMapping("/storefront")
    public String StoreFront() {
        logger.info("storefront Page Called.");
        return "StoreFront";
    }
    @GetMapping("/counter")
    public String Counter() {
        logger.info("counter Page Called.");
        return "Counter";
    }
    @GetMapping("/scanner")
    public String Scanner() {
        logger.info("scanner Page Called.");
        return "Scanner";
    }
    @GetMapping("/posTerminal")
    public String POSTerminal() {
        logger.info("posTerminal Page Called.");
        return "POSTerminal";
    }
    @GetMapping("/drawer")
    public String Drawer() {
        logger.info("drawer Page Called.");
        return "Drawer";
    }

    @GetMapping("/formsPage")
    public String froms() {
        logger.info("froms Page Called.");
        return "FormMGT";
    }

    @GetMapping("/invoiceTemp")
    public String invoiceTemp(@RequestParam("invoiceId") int invoiceId, Model model) {
        logger.info("invoiceTemp Page Called.{}", invoiceId);
        InvoiceDTO dto = invoiceService.getInvoiceData(invoiceId);
        model.addAttribute("invoice", dto);
        return "InvoiceTemplate";
    }

    @GetMapping("/editInvoice")
    public String editInvoice(@RequestParam("invoiceId") int invoiceId, Model model) {
        logger.info("editInvoice Page Called.{}", invoiceId);
        InvoiceDTO dto = invoiceService.getInvoiceData(invoiceId);
        model.addAttribute("invoice", dto);
        return "EditInvoice";
    }
}
