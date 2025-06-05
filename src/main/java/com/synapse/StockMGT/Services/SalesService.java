package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.CustomFields_customer;
import com.synapse.StockMGT.DTOs.*;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final BrandRepo brandRepo;
    private final SupplierGRNRepo supplierGRNRepo;
    private final SalesRepo salesRepo;
    private final CustomerRepo customerRepo;
    private final SoldProductRepo soldProductRepo;
    private final ItemInfoRepo itemInfoRepo;
    private final ItemRepo itemRepo;
    private final InvoiceRepo invoiceRepo;
    private final ServiceRepo serviceRepo;
    private final ItemHistoryRepo itemHistoryRepo;
    private final StoreRepo storeRepo;

    public List<SaleItemDTO> getItems() {
        List<SaleItemDTO> salesItems = new ArrayList<>();
        List<SupplierGRN> suppliers = supplierGRNRepo.findAll();

        for (SupplierGRN grn : suppliers) {
            Map<Integer, List<Item>> itemsByStore = grn.getItems().stream()
                    .collect(Collectors.groupingBy(item -> item.getStore().getStoreId()));

            for (Map.Entry<Integer, List<Item>> entry : itemsByStore.entrySet()) {
                Integer storeId = entry.getKey();
                List<Item> itemsInStore = entry.getValue();

                salesItems.add(SaleItemDTO.builder()
                        .brandName(grn.getBrandName())
                        .itemCode(grn.getItemCode())
                        .description(grn.getProductDescription())
                        .dealerPrice(grn.getDealerPrice())
                        .retailPrice(grn.getRetailPrice())
                        .quantity(itemsInStore.size())
                        .supplierGRNID(grn.getSupplierGRNId())
                        .storeId(storeId)
                        .build());
            }
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
                itemHistoryRepo.save(ItemHistory.builder()
                                .brand(supplierGRN.getBrandName())
                                .itemCode(product.getItemCode())
                                .serialNo(supplierGRN.getItems().get(q).getSerialNumber())
                                .currentState("Sold to: "+thisCustomer.getName()+"\n"+thisCustomer.getPhone())
                                .lastUpdate(LocalDate.now())
                                .build());
                itemRepo.deleteByItemId(itemID);
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

        for(Sales sale : sales){
            for(SoldProducts soldProduct : sale.getSoldProducts()){
                List<String> serials = new ArrayList<>();
                Store store = storeRepo.findById(soldProduct.getStore().getStoreId())
                        .orElseThrow(() -> new RuntimeException("Store not found"));
                for(SoldItem soldItem : soldProduct.getSoldItems()){
                    serials.add(soldItem.getSerialNumber());
                }
                SupplierGRN grn = supplierGRNRepo.findById(soldProduct.getSupplierGRNId())
                        .orElseThrow(() -> new RuntimeException("GRN not found"));
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
                        .storeAddress(store.getStoreAddress())
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