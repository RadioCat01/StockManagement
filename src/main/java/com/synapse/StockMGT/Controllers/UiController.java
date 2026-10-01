package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.InvoiceDTO;
import com.synapse.StockMGT.Services.InvoiceService;
import com.synapse.StockMGT.User.Auth.LoginAudience;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UiController {

    Logger logger = LoggerFactory.getLogger("AUDIT");
    private final InvoiceService invoiceService;

    @GetMapping("/inventory")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
    public String inventory() {
        logger.info("inventory Page Called.");
        return "inventory";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/login/application-admin";
    }

    @GetMapping("/login/application-admin")
    public String applicationAdminLogin(Model model) {
        return loginPage(model, LoginAudience.APPLICATION_ADMIN, "Application Admin Login");
    }

    @GetMapping("/login/company-admin")
    public String companyAdminLogin(Model model) {
        return loginPage(model, LoginAudience.COMPANY_ADMIN, "Company Admin Login");
    }

    @GetMapping("/login/worker")
    public String workerLogin(Model model) {
        return loginPage(model, LoginAudience.WORKER, "Worker Login");
    }

    private String loginPage(Model model, LoginAudience audience, String title) {
        model.addAttribute("loginType", audience.name());
        model.addAttribute("loginTitle", title);
        return "Login";
    }

    @GetMapping("/grnSummery")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
    public String grnSummery() {
        logger.info("grnSummery Page Called.");
        return "GRNSummery";
    }

    @GetMapping("/pos")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'CASHIER')")
    public String pos() {
        logger.info("pos Page Called.");
        return "POS";
    }

    @GetMapping("/category")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String addCategory() {
        logger.info("addCategory Page Called.");
        return "Category";
    }

    @GetMapping("/brand")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String addBrand() {
        logger.info("addBrandPage Called.");
        return "Brand";
    }

    @GetMapping("/items")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
    public String items() {
        logger.info("items Page Called.");
        return "Items";
    }

    @GetMapping("/suppliers")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
    public String supplier() {
        logger.info("supplier Page Called.");
        return "Supplier";
    }

    @GetMapping("/sales")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public String sales() {
        logger.info("sales Page Called.");
        return "Sales";
    }

    @GetMapping("/invoice")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public String invoice() {
        logger.info("invoice Page Called.");
        return "Invoice";
    }

    @GetMapping("/customerJobs")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
    public String customerJobs() {
        logger.info("customerJobs Page Called.");
        return "Job";
    }

    @GetMapping("/company")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String company() {
        logger.info("company Page Called.");
        return "Company";
    }

    @GetMapping("/subcompany")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String subCompany() {
        logger.info("subCompany Page Called.");
        return "SubCompany";
    }
    @GetMapping("/store")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String Store() {
        logger.info("store Page Called.");
        return "Store";
    }
    @GetMapping("/storefront")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String StoreFront() {
        logger.info("storefront Page Called.");
        return "StoreFront";
    }
    @GetMapping("/counter")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String Counter() {
        logger.info("counter Page Called.");
        return "Counter";
    }
    @GetMapping("/scanner")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String Scanner() {
        logger.info("scanner Page Called.");
        return "Scanner";
    }
    @GetMapping("/posTerminal")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String POSTerminal() {
        logger.info("posTerminal Page Called.");
        return "POSTerminal";
    }
    @GetMapping("/drawer")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String Drawer() {
        logger.info("drawer Page Called.");
        return "Drawer";
    }

    @GetMapping("/formsPage")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String froms() {
        logger.info("froms Page Called.");
        return "FormMGT";
    }

    @GetMapping("/invoiceTemp")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN', 'CASHIER')")
    public String invoiceTemp(@RequestParam("invoiceId") int invoiceId, Model model) {
        logger.info("invoiceTemp Page Called.{}", invoiceId);
        InvoiceDTO dto = invoiceService.getInvoiceData(invoiceId);
        model.addAttribute("invoice", dto);
        return "InvoiceTemplate";
    }

    @GetMapping("/editInvoice")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String editInvoice(@RequestParam("invoiceId") int invoiceId, Model model) {
        logger.info("editInvoice Page Called.{}", invoiceId);
        InvoiceDTO dto = invoiceService.getInvoiceData(invoiceId);
        model.addAttribute("invoice", dto);
        return "EditInvoice";
    }

    @GetMapping("/admin/users/page")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public String userAdministration() {
        return "UserAdministration";
    }
}
