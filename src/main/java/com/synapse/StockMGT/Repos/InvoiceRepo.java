package com.synapse.StockMGT.Repos;

import com.synapse.StockMGT.Models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {
}
