package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.CustomFields_customer;
import com.synapse.StockMGT.DTOs.*;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CompanyHierarchy.PosTerminal;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.*;
import com.synapse.StockMGT.User.User;
import com.synapse.StockMGT.User.Roles;
import com.synapse.StockMGT.User.AccessScopeService;
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
    private final AccessScopeService accessScope;

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getItems() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SaleItemDTO> salesItems = new ArrayList<>();
        List<Store> stores;
        if (currentUser.hasRole(Roles.CASHIER)) {
            StoreFront assignedStoreFront = currentUser.getStoreFront();
            if (assignedStoreFront == null) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "The signed-in cashier is not assigned to a store front.");
            }
            StoreFront storeFront = storeFrontRepo.findById(assignedStoreFront.getStorefrontId())
                    .orElseThrow(() -> new org.springframework.security.access.AccessDeniedException(
                            "The signed-in cashier's store front no longer exists."));
            if (storeFront == null || storeFront.getCompany() == null
                    || currentUser.getCompany() == null
                    || !storeFront.getCompany().getCompanyId().equals(currentUser.getCompany().getCompanyId())) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "The signed-in cashier is not assigned to a valid store front.");
            }
            stores = storeFront.getStore();
        } else if (currentUser.hasRole(Roles.PLATFORM_ADMIN)) {
            stores = storeRepo.findAll();
        } else {
            Integer companyId = accessScope.companyId();
            stores = storeRepo.findAllByCompany_CompanyId(companyId);
        }
        List<Item> items = stores
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



    @Transactional(readOnly = true)
    public List<Customers> getCustomers() {
        User currentUser = User.currentUser();
        if (currentUser.hasRole(Roles.PLATFORM_ADMIN)) {
            return customerRepo.findAll();
        }
        return customerRepo.findAllByCompany_CompanyId(accessScope.companyId());
    }

    @Transactional
    public int createSale(SoldDTO sale) {
        User actor = User.currentUser();
        Sales newSale = new Sales();
        List<SoldProducts> soldProducts = new ArrayList<>();
        double subTotal = 0.0;
        List<Store> saleStores = new ArrayList<>();
        Integer saleCompanyId = actor.hasRole(Roles.PLATFORM_ADMIN)
                ? null
                : accessScope.companyId();

        Optional<Customers> cus = actor.hasRole(Roles.PLATFORM_ADMIN)
                ? customerRepo.findByPhone(sale.getCustomerPhone())
                : customerRepo.findByPhoneAndCompany_CompanyId(
                        sale.getCustomerPhone(), accessScope.companyId());
        Customers thisCustomer= null;
        if (cus.isPresent()) {
            thisCustomer = cus.get();
            if (!actor.hasRole(Roles.PLATFORM_ADMIN)
                    && (thisCustomer.getCompany() == null
                    || !thisCustomer.getCompany().getCompanyId().equals(accessScope.companyId()))) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "The selected customer belongs to another company.");
            }
            newSale.setCustomer(cus.get());
        }else {
            Customers newCustomer = Customers.builder()
                    .name(sale.getCustomerName())
                    .phone(sale.getCustomerPhone())
                    .address(sale.getCustomerAddress())
                    .company(actor.hasRole(Roles.PLATFORM_ADMIN) ? null : actor.getCompany())
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
            if (product.getSelectedQuantity() <= 0) {
                throw new IllegalArgumentException("Sale quantities must be positive.");
            }
            List<SoldItem> soldItems = new ArrayList<>();
            Store store = accessScope.requireStore(product.getStoreId());
            if (!actor.hasRole(Roles.PLATFORM_ADMIN)
                    && (supplierGRN.getCompany() == null
                    || !supplierGRN.getCompany().getCompanyId().equals(accessScope.companyId()))) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "The selected stock does not belong to your company.");
            }
            if (saleCompanyId == null) {
                saleCompanyId = store.getCompany().getCompanyId();
            } else if (!saleCompanyId.equals(store.getCompany().getCompanyId())) {
                throw new IllegalArgumentException("A sale cannot contain stock from multiple companies.");
            }
            List<Item> availableItems = supplierGRN.getItems().stream()
                    .filter(item -> item.getStore() != null
                            && item.getStore().getStoreId().equals(store.getStoreId()))
                    .limit(product.getSelectedQuantity())
                    .toList();
            if (availableItems.size() != product.getSelectedQuantity()) {
                throw new IllegalArgumentException(
                        "The selected GRN does not have enough unsold stock at this location.");
            }
            if (thisCustomer.getCompany() == null) {
                thisCustomer.setCompany(store.getCompany());
            }
            if (thisCustomer.getSubCompany() == null) {
                thisCustomer.setSubCompany(store.getSubCompany());
            }
            newSale.setCompany(store.getCompany());
            newSale.setSubCompany(store.getSubCompany());
            saleStores.add(store);

            for(Item item : availableItems){
                soldItems.add(SoldItem.builder()
                                .itemCode(product.getItemCode())
                                .serialNumber(item.getSerialNumber())
                                .supplierGRNId(supplierGRN.getSupplierGRNId())
                                .company(store.getCompany())
                                .subCompany(store.getSubCompany())
                                .build());
                int itemID = item.getItemId();
                histories.add(ItemHistory.builder()
                                .brand(supplierGRN.getBrandName())
                                .itemCode(product.getItemCode())
                                .serialNo(item.getSerialNumber())
                                .currentState("Sold to: "+thisCustomer.getName()+"\n"+thisCustomer.getPhone())
                                .lastUpdate(LocalDate.now())
                                .company(store.getCompany())
                                .subCompany(store.getSubCompany())
                                .build());
                itemIdsToDelete.add(itemID);
            }
            soldProducts.add(SoldProducts.builder()
                     .itemCode(product.getItemCode())
                     .soldItems(soldItems)
                     .supplierGRNId(supplierGRN.getSupplierGRNId())
                     .store(store)
                     .company(store.getCompany())
                     .subCompany(store.getSubCompany())
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
        if (saleCompanyId == null) {
            throw new IllegalArgumentException("A sale must include at least one product.");
        }
        newSale.setSaleType(sale.getRetail() ? "Retail" : "Dealer");
        newSale.setSoldProducts(soldProducts);
        newSale.setPoReference(sale.getPoReference());
        String invoiceNumber = generateInvoiceNumber(sale.getPoReference());
        newSale.setInvoiceNumber(invoiceNumber);
        Sales newSaleObj = salesRepo.save(newSale);

        for (ServiceChargeDTO service : sale.getServiceCharges()) {
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
                        .company(saleStores.get(0).getCompany())
                        .subCompany(saleStores.get(0).getSubCompany())
                        .build();

        for (ServiceChargeDTO service : sale.getServiceCharges()) {
            invoice.getServices().add(Services.builder()
                            .serviceDescription(service.getDescription())
                            .chargeAmount(service.getChargeAmount())
                            .build());
            invoice.getServices().get(invoice.getServices().size() - 1).setCompany(invoice.getCompany());
            invoice.getServices().get(invoice.getServices().size() - 1).setSubCompany(invoice.getSubCompany());

        }
        return invoiceRepo.save(invoice).getInvoiceId();
    }

    public List<?> getSaleReport() {
        List<SalesReportDTO> reports = new ArrayList<>();
        User currentUser = User.currentUser();
        List<Sales> sales = currentUser.hasRole(Roles.PLATFORM_ADMIN)
                ? salesRepo.findAll()
                : salesRepo.findAllByCompany_CompanyId(accessScope.companyId());

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