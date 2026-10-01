package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.InvoiceDTO;
import com.synapse.StockMGT.DTOs.ProductInvoiceDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Repos.InvoiceRepo;
import com.synapse.StockMGT.Repos.ItemInfoRepo;
import com.synapse.StockMGT.Repos.SupplierGRNRepo;
import com.synapse.StockMGT.User.AccessScopeService;
import com.synapse.StockMGT.User.Roles;
import com.synapse.StockMGT.User.User;
import com.synapse.StockMGT.Util.WarrantyCal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;
    private final ItemInfoRepo itemInfoRepo;
    private final SupplierGRNRepo supplierGRNRepo;
    private final WarrantyCal warrantyCal;
    private final AccessScopeService accessScope;

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoices() {
        User user = User.currentUser();
        List<Invoice> invoices = user.hasRole(Roles.PLATFORM_ADMIN)
                ? invoiceRepo.findAll()
                : invoiceRepo.findAllByCompany_CompanyId(accessScope.companyId());
        return invoices.stream()
                .filter(this::canAccessInvoice)
                .map(invoice -> InvoiceDTO.builder()
                        .invoiceId(invoice.getInvoiceId())
                        .poReference(invoice.getPoReference())
                        .customerName(invoice.getCustomerName())
                        .customerPhone(invoice.getCustomerPhone())
                        .invoiceNumber(invoice.getInvoiceNumber())
                        .invoiceDate(invoice.getInvoiceDate())
                        .paymentTerms(invoice.getPaymentTerms())
                        .subTotal(invoice.getSubTotal())
                        .vat(invoice.getVat())
                        .saleType(invoice.getSales().getSaleType())
                        .totalInvoice(invoice.getTotalInvoice())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoices(Integer id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow();
        return getInvoiceData(invoice);
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceData(int invoiceId) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return getInvoiceData(invoice);
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceData(Invoice invoice) {
        requireInvoiceAccess(invoice);
        List<ProductInvoiceDTO> invoicingProducts = new ArrayList<>();
        double total = 0.0;
        double unitPrice = 0.0;


        for(SoldProducts products :  invoice.getSales().getSoldProducts()) {
            String ItemCode = products.getItemCode();
            String description = itemInfoRepo.findByItemCode(ItemCode)
                    .orElseThrow().getItemDescription();
            String salesType = invoice.getSales().getSaleType();

            SupplierGRN supplierGRN = supplierGRNRepo.findById(products.getSupplierGRNId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            if(salesType.equals("Retail")) {
                total = products.getSoldItems().size() * supplierGRN.getRetailPrice();
                unitPrice = supplierGRN.getRetailPrice();
            }else {
                total = products.getSoldItems().size() * supplierGRN.getDealerPrice();
                unitPrice = supplierGRN.getDealerPrice();
            }

            List<String> serials = products.getSoldItems().stream()
                            .map(SoldItem::getSerialNumber)
                            .toList();
            String serial = String.join(",", serials);


            invoicingProducts.add(ProductInvoiceDTO.builder()
                    .productDescription(description)
                    .warranty(supplierGRN.getWarranty())
                    .unitPrice(unitPrice)
                    .total(total)
                    .serials(serial)
                    .quantity(products.getSoldItems().size())
                    .build());
        }

        for (Services services : invoice.getServices()) {
           invoicingProducts.add(ProductInvoiceDTO.builder()
                           .productDescription(services.getServiceDescription())
                           .total(services.getChargeAmount())
                           .unitPrice(0.00)
                           .serials("N/A")
                           .warranty("N/A")
                           .quantity(0)
                           .build());
        }

        return InvoiceDTO.builder()
                .invoiceId(invoice.getInvoiceId())
                .poReference(invoice.getPoReference())
                .poDate(invoice.getPoDate())
                .customerName(invoice.getCustomerName())
                .customerPhone(invoice.getCustomerPhone())
                .customerAddress(invoice.getCustomerAddress())
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceDate(invoice.getInvoiceDate())
                .paymentTerms(invoice.getPaymentTerms())
                .salesPerson(invoice.getSalesPerson())
                .subTotal(invoice.getSubTotal())
                .vat(invoice.getVat())
                .saleType(invoice.getSales().getSaleType())
                .totalInvoice(invoice.getTotalInvoice())
                .products(invoicingProducts)
                .build();
    }

    @Transactional
    public String updateInvoice(InvoiceDTO invoice) {
       Invoice existingInvoice = invoiceRepo.findById(invoice.getInvoiceId())
               .orElseThrow(() -> new RuntimeException("Invoice not found"));
       requireInvoiceAccess(existingInvoice);

       existingInvoice.setCustomerName(invoice.getCustomerName());
       existingInvoice.setCustomerPhone(invoice.getCustomerPhone());
       existingInvoice.setCustomerAddress(invoice.getCustomerAddress());
       existingInvoice.setPaymentTerms(invoice.getPaymentTerms());
       existingInvoice.getSales().setSaleType(invoice.getSaleType());
       existingInvoice.setSubTotal(invoice.getSubTotal());
       existingInvoice.setVat(invoice.getVat());
       existingInvoice.setTotalInvoice(invoice.getTotalInvoice());

       return invoiceRepo.save(existingInvoice).getInvoiceNumber();
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceByNumber(String number) {
        return invoiceRepo.findByInvoiceNumber(number).map(invoice -> {
            requireInvoiceAccess(invoice);
            List<SoldProducts> products = invoice.getSales().getSoldProducts();
            List<Integer> grnIds = new ArrayList<>();
            for (SoldProducts product : products) {
                for (SoldItem soldItem : product.getSoldItems()) {
                    grnIds.add(soldItem.getSupplierGRNId());
                }
            }

            List<SupplierGRN> grns = supplierGRNRepo.findAllById(grnIds);
            java.util.Map<Integer, SupplierGRN> grnMap = new java.util.HashMap<>();
            for (SupplierGRN grn : grns) {
                grnMap.put(grn.getSupplierGRNId(), grn);
            }

            List<ProductInvoiceDTO> items = new ArrayList<>();
            for(SoldProducts product:products) {
                for(SoldItem soldItem : product.getSoldItems()) {
                    SupplierGRN grn = grnMap.get(soldItem.getSupplierGRNId());
                    if (grn == null) {
                        throw new RuntimeException("Supplier not found");
                    }
                    items.add(ProductInvoiceDTO.builder()
                                    .productDescription(grn.getProductDescription())
                                    .serials(soldItem.getSerialNumber())
                                    .warranty(grn.getWarranty())
                                    .invoiceDate(invoice.getInvoiceDate())
                                    .grnDate(grn.getGrnDate())
                                    .supplierWarrantyUntil(warrantyCal.getSupplierWarranty(grn))
                                    .sellerWarrantyUntil(warrantyCal.getSellerWarranty(grn,invoice))
                                    .remainingSellerWarranty(warrantyCal.getRemainingSellerWarrantyInMonths(grn,invoice))
                                    .remainingSupplierWarranty(warrantyCal.getRemainingSupplierWarrantyInMonths(grn))
                                    .build());
                }
            }
            return InvoiceDTO.builder()
                    .customerName(invoice.getCustomerName())
                    .customerPhone(invoice.getCustomerPhone())
                    .invoiceNumber(invoice.getInvoiceNumber())
                    .invoiceDate(invoice.getInvoiceDate())
                    .invoiceId(invoice.getInvoiceId())
                    .poDate(invoice.getPoDate())
                    .products(items)
                    .build();
                })
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    private boolean canAccessInvoice(Invoice invoice) {
        User user = User.currentUser();
        if (user.hasRole(Roles.PLATFORM_ADMIN)) {
            return true;
        }
        if (user.getCompany() == null || invoice.getCompany() == null
                || !user.getCompany().getCompanyId().equals(invoice.getCompany().getCompanyId())) {
            return false;
        }
        if (!user.hasRole(Roles.CASHIER)) {
            return true;
        }
        return invoice.getSales().getSoldProducts().stream()
                .map(SoldProducts::getStore)
                .filter(java.util.Objects::nonNull)
                .anyMatch(store -> store.getStoreFronts().stream().anyMatch(front ->
                        user.getStoreFront() != null
                                && front.getStorefrontId().equals(user.getStoreFront().getStorefrontId())));
    }

    private void requireInvoiceAccess(Invoice invoice) {
        if (!canAccessInvoice(invoice)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You cannot access this company's invoice.");
        }
    }
}
