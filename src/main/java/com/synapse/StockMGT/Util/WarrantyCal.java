package com.synapse.StockMGT.Util;

import com.synapse.StockMGT.Models.Invoice;
import com.synapse.StockMGT.Models.SupplierGRN;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class WarrantyCal {

    public LocalDate getSupplierWarranty(SupplierGRN grn) {
        if (grn == null || grn.getGrnDate() == null || grn.getWarranty() == null) {
            throw new IllegalArgumentException("GRN, grnDate and warranty must not be null");
        }

        Period warrantyPeriod = parseWarrantyPeriod(grn.getWarranty());
        return grn.getGrnDate().plus(warrantyPeriod);
    }

    public LocalDate getSellerWarranty(SupplierGRN grn, Invoice invoice) {
        if (grn == null || grn.getSellerWarranty() == null || invoice == null || invoice.getInvoiceDate() == null) {
            throw new IllegalArgumentException("GRN, grnDate and warranty must not be null");
        }

        Period warrantyPeriod = parseWarrantyPeriod(grn.getSellerWarranty());
        return invoice.getInvoiceDate().plus(warrantyPeriod);
    }

    private Period parseWarrantyPeriod(String warranty) {
        warranty = warranty.trim().toLowerCase();

        if (warranty.matches("\\d+\\s*year[s]?")) {
            int years = Integer.parseInt(warranty.split("\\s+")[0]);
            return Period.ofYears(years);
        } else if (warranty.matches("\\d+\\s*month[s]?")) {
            int months = Integer.parseInt(warranty.split("\\s+")[0]);
            return Period.ofMonths(months);
        } else if (warranty.matches("\\d+\\s*day[s]?")) {
            int days = Integer.parseInt(warranty.split("\\s+")[0]);
            return Period.ofDays(days);
        } else {
            throw new IllegalArgumentException("Unsupported warranty format: " + warranty);
        }
    }

    public String getRemainingSellerWarrantyInMonths(SupplierGRN grn, Invoice invoice) {
        if (grn == null || grn.getSellerWarranty() == null || invoice == null || invoice.getInvoiceDate() == null) {
            throw new IllegalArgumentException("GRN, seller warranty and invoice date must not be null");
        }

        Period warrantyPeriod = parseWarrantyPeriod(grn.getSellerWarranty());
        LocalDate expiryDate = invoice.getInvoiceDate().plus(warrantyPeriod);
        LocalDate today = LocalDate.now();

        if (today.isAfter(expiryDate)) {
            return "Expired";
        }

        long monthsRemaining = ChronoUnit.MONTHS.between(today.withDayOfMonth(1), expiryDate.withDayOfMonth(1));
        return monthsRemaining + " month" + (monthsRemaining != 1 ? "s" : "");
    }

    public String getRemainingSupplierWarrantyInMonths(SupplierGRN grn) {
        if (grn == null || grn.getWarranty() == null || grn.getGrnDate() == null) {
            throw new IllegalArgumentException("GRN, warranty and GRN date must not be null");
        }

        Period warrantyPeriod = parseWarrantyPeriod(grn.getWarranty());
        LocalDate expiryDate = grn.getGrnDate().plus(warrantyPeriod);
        LocalDate today = LocalDate.now();

        if (today.isAfter(expiryDate)) {
            return "Expired";
        }

        long monthsRemaining = ChronoUnit.MONTHS.between(
                today.withDayOfMonth(1),
                expiryDate.withDayOfMonth(1)
        );

        return monthsRemaining + " month" + (monthsRemaining != 1 ? "s" : "");
    }

}
