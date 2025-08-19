package com.synapse.StockMGT.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceDTO {
    private int invoiceId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private LocalDate poDate;
    private String poReference;
    private LocalDate grnDate;

    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String paymentTerms;
    private String salesPerson;
    private String saleType;

    private double subTotal;
    private double vat;
    private double totalInvoice;

    private List<ProductInvoiceDTO> products;
}
