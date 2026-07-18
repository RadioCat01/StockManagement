package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.CustomFields_customer;
import com.synapse.StockMGT.DTOs.*;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CompanyHierarchy.PosTerminal;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.*;
import com.synapse.StockMGT.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SupplierGRNRepo supplierGRNRepo;
    private final SalesRepo salesRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;
    private final InvoiceRepo invoiceRepo;
    private final ItemHistoryRepo itemHistoryRepo;
    private final StoreRepo storeRepo;
    private final StoreFrontRepo storeFrontRepo;

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getItems() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SaleItemDTO> salesItems = new ArrayList<>();
        int storeFrontID = currentUser.getStoreFront().getStorefrontId();
        StoreFront storeFront = storeFrontRepo.findById(storeFrontID).orElseThrow(()->new RuntimeException("Store front not found"));
        List<Item> items = storeFront.getStore()
                .stream().flatMap(store -> store.getItems().stream())
                .toList();

        Map<String, List<Item>> groupedItems = items.stream()
                .filter(item -> item.getSupplierGRN() != null && item.getStore() != null)
                .collect(Collectors.groupingBy(item ->
                        item.getStore().getStoreId() + "-" + item.getSupplierGRN().getSupplierGRNId()
                ));

        for (Map.Entry<String, List<Item>> entry : groupedItems.entrySet()) {
            List<Item> groupedItemList = entry.getValue();
            Item representative = groupedItemList.get(0);

            SupplierGRN grn = representative.getSupplierGRN();
            Store store = representative.getStore();

            salesItems.add(SaleItemDTO.builder()
                    .brandName(grn.getBrandName())
                    .itemCode(grn.getItemCode())
                    .description(grn.getProductDescription())
                    .dealerPrice(grn.getDealerPrice())
                    .retailPrice(grn.getRetailPrice())
                    .quantity(groupedItemList.size())
                    .supplierGRNID(grn.getSupplierGRNId())
                    .storeId(store.getStoreId())
                    .build());
        }
        return salesItems;
    }



    public List<Customers> getCustomers() {
        return customerRepo.findAll();
    }

    @Transactional
    public int createSale(SoldDTO sale) {
        Sales newSale = new Sales();
        List<SoldProducts> soldProducts = new ArrayList<>();
        double subTotal = 0.0;

        Optional<Customers> cus = customerRepo.findByPhone(sale.getCustomerPhone());
        Customers thisCustomer= null;
        if (cus.isPresent()) {
            thisCustomer = cus.get();
            newSale.setCustomer(cus.get());
        }else {
            Customers newCustomer = Customers.builder()
                    .name(sale.getCustomerName())
                    .phone(sale.getCustomerPhone())
                    .address(sale.getCustomerAddress())
                    .build();
            if(sale.getCustomFields()!=null){
                for (CustomFields_customer customer : sale.getCustomFields()){
                    newCustomer.getCustomFields().add(CustomFields_customer.builder()
                            .fieldName(customer.getFieldName())
                            .fieldType(customer.getFieldType())
                            .fieldValue(customer.getFieldValue())
                            .required(customer.isRequired())
                            .enabled(customer.isEnabled())
                            .build());
                }
            }
            thisCustomer = newCustomer;
            newSale.setCustomer(newCustomer);
        }

        List<ItemHistory> histories = new ArrayList<>();
        List<Integer> itemIdsToDelete = new ArrayList<>();

        for (SoldProductDTO product : sale.getProducts()){
            SupplierGRN supplierGRN = supplierGRNRepo.findById(product.getSupplierGRNID())
                    .orElseThrow(() -> new RuntimeException("GRN not found"));

            List<SoldItem> soldItems = new ArrayList<>();
            Store store = storeRepo.findById(product.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));

            for(int q=0; q <= product.getSelectedQuantity()-1; q++){
                soldItems.add(SoldItem.builder()
                                .itemCode(product.getItemCode())
                                .serialNumber(supplierGRN.getItems().get(q).getSerialNumber())
                                .supplierGRNId(supplierGRN.getSupplierGRNId())
                                .build());
                int itemID = supplierGRN.getItems().get(q).getItemId();
                histories.add(ItemHistory.builder()
                                .brand(supplierGRN.getBrandName())
                                .itemCode(product.getItemCode())
                                .serialNo(supplierGRN.getItems().get(q).getSerialNumber())
                                .currentState("Sold to: "+thisCustomer.getName()+"\n"+thisCustomer.getPhone())
                                .lastUpdate(LocalDate.now())
                                .build());
                itemIdsToDelete.add(itemID);
            }
            soldProducts.add(SoldProducts.builder()
                     .itemCode(product.getItemCode())
                     .soldItems(soldItems)
                     .supplierGRNId(supplierGRN.getSupplierGRNId())
                     .store(store)
                     .build());

            if(sale.getRetail().equals(true)) {
                subTotal += product.getSelectedQuantity() * supplierGRN.getRetailPrice();
            }
            else {
                subTotal += product.getSelectedQuantity() * supplierGRN.getDealerPrice();
            }
        }
        if (!histories.isEmpty()) {
            itemHistoryRepo.saveAll(histories);
        }
        if (!itemIdsToDelete.isEmpty()) {
            itemRepo.deleteAllByIdInBatch(itemIdsToDelete);
        }
        newSale.setSaleType(sale.getRetail() ? "Retail" : "Dealer");
        newSale.setSoldProducts(soldProducts);
        newSale.setPoReference(sale.getPoReference());
        String invoiceNumber = generateInvoiceNumber(sale.getPoReference());
        newSale.setInvoiceNumber(invoiceNumber);
        Sales newSaleObj = salesRepo.save(newSale);


        for(ServiceChargeDTO service : sale.getServiceCharges()){
            subTotal += service.getChargeAmount();
        }

        Invoice invoice =Invoice.builder()
                        .customerName(sale.getCustomerName())
                        .customerPhone(sale.getCustomerPhone())
                        .customerAddress(sale.getCustomerAddress())
                        .invoiceNumber(invoiceNumber)
                        .invoiceDate(newSaleObj.getSoldDate())
                        .paymentTerms(sale.getPaymentTerms())
                        .salesPerson("TO DO!")
                        .subTotal(subTotal)
                        .vat(0.0)
                        .totalInvoice(subTotal)
                        .sales(newSale)
                        .poDate(LocalDate.now())
                        .poReference(sale.getPoReference())
                        .build();

        for (ServiceChargeDTO service : sale.getServiceCharges()) {
            invoice.getServices().add(Services.builder()
                            .serviceDescription(service.getDescription())
                            .chargeAmount(service.getChargeAmount())
                            .build());

        }
        return invoiceRepo.save(invoice).getInvoiceId();
    }

    public List<?> getSaleReport() {
        List<SalesReportDTO> reports = new ArrayList<>();
        List<Sales> sales = salesRepo.findAll();

        List<Integer> grnIds = sales.stream()
                .flatMap(s -> s.getSoldProducts().stream())
                .map(SoldProducts::getSupplierGRNId)
                .distinct()
                .toList();

        List<SupplierGRN> grnList = supplierGRNRepo.findAllById(grnIds);
        Map<Integer, SupplierGRN> grnMap = new HashMap<>();
        for (SupplierGRN grn : grnList) {
            grnMap.put(grn.getSupplierGRNId(), grn);
        }

        for(Sales sale : sales){
            for(SoldProducts soldProduct : sale.getSoldProducts()){
                List<String> serials = new ArrayList<>();
                Store store = soldProduct.getStore();
                if (store == null) {
                    throw new RuntimeException("Store not found");
                }
                for(SoldItem soldItem : soldProduct.getSoldItems()){
                    serials.add(soldItem.getSerialNumber());
                }
                SupplierGRN grn = grnMap.get(soldProduct.getSupplierGRNId());
                if (grn == null) {
                    throw new RuntimeException("GRN not found");
                }
                reports.add(SalesReportDTO.builder()
                        .brand(grn.getBrandName())
                        .description(grn.getProductDescription())
                        .itemCode(soldProduct.getItemCode())
                        .customerName(sale.getCustomer().getName())
                        .customerPhone(sale.getCustomer().getPhone())
                        .customerAddress(sale.getCustomer().getAddress())
                        .soldDate(sale.getSoldDate())
                        .serialNumbers(String.join(",", serials))
                        .storeName(store.getSubCompany().getSubCompanyName())
                        .storeAddress(store.getStoreName())
                        .poReference(sale.getPoReference())
                        .invoiceNumber(sale.getInvoiceNumber())
                        .build());
            }
        }

        return reports;
    }
    private String generateInvoiceNumber(String po) {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        return String.format("INV-%s%s", datePart,po);
    }
}