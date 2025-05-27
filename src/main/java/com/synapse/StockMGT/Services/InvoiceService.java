package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.InvoiceDTO;
import com.synapse.StockMGT.DTOs.ProductInvoiceDTO;
import com.synapse.StockMGT.DTOs.ServiceChargeDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Repos.InvoiceRepo;
import com.synapse.StockMGT.Repos.ItemInfoRepo;
import com.synapse.StockMGT.Repos.SalesRepo;
import com.synapse.StockMGT.Repos.SupplierGRNRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;
    private final ItemInfoRepo itemInfoRepo;
    private final SupplierGRNRepo supplierGRNRepo;

    public List<?> getAllInvoices() {
        List<Invoice> invoices = invoiceRepo.findAll();
        List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
        for (Invoice invoice : invoices) {
            invoiceDTOs.add(getInvoiceData(invoice.getInvoiceId()));
        }
        return invoiceDTOs;
    }

    public InvoiceDTO getInvoices(Integer id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow();
        return getInvoiceData(invoice.getInvoiceId());
    }

    public InvoiceDTO getInvoiceData(int invoiceId) {
        List<ProductInvoiceDTO> invoicingProducts = new ArrayList<>();
        double total = 0.0;
        double unitPrice = 0.0;

        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));


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
                .invoiceId(invoiceId)
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
}
