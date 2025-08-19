package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.CustomFields_grn;
import com.synapse.StockMGT.DTOs.SupplierGRNDTO;
import com.synapse.StockMGT.Models.Item;
import com.synapse.StockMGT.Models.ItemInfo;
import com.synapse.StockMGT.Models.SupplierGRN;
import com.synapse.StockMGT.Repos.ItemInfoRepo;
import com.synapse.StockMGT.Repos.SupplierGRNRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierGRNService {

    private final SupplierGRNRepo supplierGRNRepo;
    private final ItemInfoRepo itemInfoRepo;

    public ResponseEntity<?> getAllGRNsForSupplier(Integer supplierId) {
        List<SupplierGRN> grnList = supplierGRNRepo.findBySupplierId(supplierId);
        List<SupplierGRNDTO> grnDTOList = new ArrayList<>();

        if (grnList.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No supplier found");
            return ResponseEntity.ok(response);
        }
        for (SupplierGRN grn : grnList) {
            SupplierGRNDTO supplierGRNDTO =SupplierGRNDTO.builder()
                    .categoryName(grn.getCategoryName())
                    .brandName(grn.getBrandName())
                    .productDescription(grn.getProductDescription())
                    .warranty(grn.getWarranty())
                    .quantity(grn.getQuantity())
                    .cost(grn.getCost())
                    .dealerPrice(grn.getDealerPrice())
                    .retailPrice(grn.getRetailPrice())
                    .supplierPayment(grn.getSupplierPayment())
                    .paymentStatus(grn.getPaymentStatus())
                    .serialNumbers(grn.getSerialNumberList())
                    .items(grn.getItems())
                    .supplierInvoice(grn.getSupplierInvoiceNumber())
                    .grnDate(grn.getGrnDate())
                    .storeId(grn.getStore())
                    .build();

            if(grn.getCustomFields() != null) {
                for (CustomFields_grn customField : grn.getCustomFields()) {
                    switch (customField.getFieldName()){
                        case "Supplier Invoice":
                            supplierGRNDTO.setSupplierInvoice(customField.getFieldValue());
                            break;
                    }
                }
            }
            grnDTOList.add(supplierGRNDTO);
        }
        return ResponseEntity.ok(grnDTOList);
    }
}
