package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.CustomFields_grn;
import com.synapse.StockMGT.CustomFields.CustomFields_item;
import com.synapse.StockMGT.CustomFields.CustomFields_supplier;
import com.synapse.StockMGT.DTOs.*;
import com.synapse.StockMGT.DTOs.CategotyDTO.CategoryDTO;
import com.synapse.StockMGT.DTOs.CategotyDTO.ItemInfoDTO;
import com.synapse.StockMGT.Models.*;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ItemInfoRepo itemInfoRepo;
    private final StoreRepo storeRepo;
    private final SubComRepo subComRepo;
    private final SupplierRepo supplierRepo;
    private final SupplierGRNRepo supplierGRNRepo;
    private final ItemHistoryRepo itemHistoryRepo;

    public List<FlatCatDTO> getFlatCat() {
        List<FlatCatDTO> flatCatDTOList = new ArrayList<>();
        List<Category> categoryList = categoryRepo.findAll();

        for (Category category : categoryList) {
            if (!category.getBrands().isEmpty()) {
                for (Brand brand : category.getBrands()) {
                    if (!brand.getSupplierGRNs().isEmpty()) {
                        for (SupplierGRN info : brand.getSupplierGRNs()) {
                            flatCatDTOList.add(FlatCatDTO.builder()
                                    .categoryName(category.getCategoryName())
                                    .brandName(brand.getBrandName())
                                    .itemCode(info.getItemCode())
                                    .itemDescription(info.getProductDescription())
                                    .quantity(info.getItems().toArray().length)
                                    .date(info.getGrnDate())
                                    .build());
                        }
                    } else {
                        for (ItemInfo info : brand.getItemInfos()) {
                            flatCatDTOList.add(FlatCatDTO.builder()
                                    .categoryName(category.getCategoryName())
                                    .brandName(brand.getBrandName())
                                    .itemCode(info.getItemCode())
                                    .itemDescription(info.getItemDescription())
                                    .quantity(0)
                                    .build());
                        }
                    }
                }
            } else {
                flatCatDTOList.add(FlatCatDTO.builder()
                        .categoryName(category.getCategoryName())
                        .brandName("N/A")
                        .itemCode("N/A")
                        .itemDescription("N/A")
                        .quantity(0)
                        .build());
            }
        }
        return flatCatDTOList;
    }

    public String createCategory(String categoryName) {
        Optional<Category> existingCategory = categoryRepo.findByCategoryName(categoryName);
        if(existingCategory.isPresent()){
            return existingCategory.get().getCategoryName();
        }else {
            return categoryRepo.save(Category.builder()
                            .categoryName(categoryName)
                            .build()).getCategoryName();
        }
    }

    public String createBrand(BrandDTO brandDTO) {
        List<Category> categoryList = categoryRepo.findAll();
        List<Brand> brandList = categoryList.stream().map(Category::getBrands).flatMap(List::stream).toList();
        Brand existingBrand = brandList.stream().filter(brand -> brand
                .getBrandName().toLowerCase().trim().equals(brandDTO
                        .getBrandName().toLowerCase().trim())).findFirst().orElse(null);

        if (existingBrand != null) {

            List<ItemInfo> existingItemInfo = itemInfoRepo.findByBrand(existingBrand);
            for (ItemInfo itemInfo : existingItemInfo) {
                if (!itemInfo.getItemCode().toLowerCase().trim().equals(brandDTO.getItemCode().toLowerCase().trim())) {
                    ItemInfo savedItemInfo = ItemInfo.builder()
                            .itemDescription(brandDTO.getProductDescription())
                            .itemCode(brandDTO.getItemCode())
                            .brand(existingBrand)
                            .build();

                    return setCustomFields(brandDTO, savedItemInfo);
                } else {
                    return null;
                }
            }

        } else {
            Brand newBrand = Brand.builder()
                            .category(categoryRepo.findById(brandDTO.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found")))
                            .brandName(brandDTO.getBrandName()).build();


            ItemInfo savedItemInfo = ItemInfo.builder()
                    .itemDescription(brandDTO.getProductDescription())
                    .itemCode(brandDTO.getItemCode())
                    .brand(brandRepo.save(newBrand))
                    .build();

            return setCustomFields(brandDTO, savedItemInfo);
        }
        return null;
    }

    private String setCustomFields(BrandDTO brandDTO, ItemInfo savedItemInfo) {
        if(brandDTO.getCustomFields()!=null){
            for (CustomFields_item customField : brandDTO.getCustomFields()) {
                savedItemInfo.getCustomFields().add(CustomFields_item.builder()
                        .fieldName(customField.getFieldName())
                        .fieldType(customField.getFieldType())
                        .fieldValue(customField.getFieldValue())
                        .required(customField.isRequired())
                        .enabled(customField.isEnabled())
                        .build());
            }
        }

        return itemInfoRepo.save(savedItemInfo).getBrand().getBrandName();
    }

    @Transactional
    public String createItem(ItemDTO itemDTO) {
        ItemInfo itemInfo = itemInfoRepo.findByItemCode(itemDTO.getItemCode())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Supplier supplier = supplierRepo.findById(itemDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Brand brand = brandRepo.findById(itemDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Store store = storeRepo.findById(itemDTO.getStore())
                .orElseThrow(() -> new RuntimeException("Store not found"));


        String grnDateStr = itemDTO.getGrnDate();
        LocalDate date;
        if (grnDateStr != null) {
            String datePart = grnDateStr.split("T")[0];
            date = LocalDate.parse(datePart);
        } else {
            date = LocalDate.now();
        }

        SupplierGRN savingGRN = SupplierGRN.builder()
                .supplierId(itemDTO.getSupplierId())
                .categoryName(categoryRepo.findById(itemDTO.getCategoryId()).orElseThrow().getCategoryName())
                .brandName(brand.getBrandName())
                .productDescription(itemInfo.getItemDescription())
                .warranty(itemDTO.getWarranty())
                .sellerWarranty(itemDTO.getSellerWarranty())
                .quantity(itemDTO.getQuantity())
                .cost(itemDTO.getItemCost())
                .dealerPrice(itemDTO.getDealerPrice())
                .retailPrice(itemDTO.getRetailPrice())
                .supplierPayment(itemDTO.getSupplierPayment())
                .paymentStatus(itemDTO.getPaymentStatus())
                .itemCode(itemDTO.getItemCode())
                .grnDate(date)
                .store(itemDTO.getStore())
                .supplierInvoiceNumber(itemDTO.getSupplierInvoiceNumber())
                .build();

        if(itemDTO.getCustomFields()!=null){
            for (CustomFields_grn customField : itemDTO.getCustomFields()) {
                savingGRN.getCustomFields().add(CustomFields_grn.builder()
                        .fieldName(customField.getFieldName())
                        .fieldType(customField.getFieldType())
                        .fieldValue(customField.getFieldValue())
                        .required(customField.isRequired())
                        .enabled(customField.isEnabled())
                        .build());
            }
        }

        List<String> serials = new ArrayList<>();
        List<Item> newItems = new ArrayList<>();

        if (Boolean.TRUE.equals(itemDTO.getHasSerialNumbers())) {
            serials.addAll(itemDTO.getSerialNumbers());
        } else {
            itemDTO.setSerialNumbers(null);
            for (int i = 1; i <= itemDTO.getQuantity(); i++) {
                serials.add("SN-" + UUID.randomUUID().toString().substring(0, 6));
            }
        }

        for (String serial : serials) {
            Item newItem = Item.builder()
                    .serialNumber(serial)
                    .supplier(supplier)
                    .cost(itemDTO.getItemCost())
                    .dealerPrice(itemDTO.getDealerPrice())
                    .retailPrice(itemDTO.getRetailPrice())
                    .itemInfo(itemInfo)
                    .store(store)
                    .lastUpdate(LocalDate.now())
                    .currentPosition(store.getSubCompany().getSubCompanyName())
                    .stockType(itemDTO.getStockType())
                    .supplierGRN(savingGRN)
                    .build();
            newItems.add(newItem);
        }
            savingGRN.setSerialNumberList(String.join("\n", serials));
            savingGRN.setItems(newItems);
            SupplierGRN savedGRN = supplierGRNRepo.save(savingGRN);
            brand.getSupplierGRNs().add(savedGRN);
            brandRepo.save(brand);
            recordItemHistory(newItems,brand,itemInfo, store);
            return itemDTO.getItemCode();
    }

    public void recordItemHistory(List<Item> item, Brand brand, ItemInfo itemInfo, Store store) {
        for (Item i : item) {
            itemHistoryRepo.save(ItemHistory.builder()
                    .brand(brand.getBrandName())
                    .itemCode(itemInfo.getItemCode())
                    .serialNo(i.getSerialNumber())
                    .currentState("Added to stock: "+store.getSubCompany().getSubCompanyName()+" - "+store.getStoreAddress())
                    .lastUpdate(LocalDate.now())
                    .build());
        }
    }

    public Supplier saveSupplier(SupplierReqDTO supplierDTO){
        Supplier supplier = Supplier.builder()
                .name(supplierDTO.getName())
                .address(supplierDTO.getAddress())
                .contactName(supplierDTO.getContactName())
                .contactNumber(String.valueOf(supplierDTO.getContactNumber()))
                .paymentTerms(supplierDTO.getPaymentTerms())
                .period(supplierDTO.getPeriod())
                .build();
        if (supplierDTO.getCustomFields() != null) {
            for (CustomFields_supplier field : supplierDTO.getCustomFields()) {
                supplier.getCustomFields().add(CustomFields_supplier.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldValue(field.getFieldValue())
                        .required(field.isRequired())
                        .enabled(field.isEnabled())
                        .build());
            }
        }
        return supplierRepo.save(supplier);
    }

    public List<SupplierResDTO> getSupplierRES() {
        return supplierRepo.findAll().stream()
                .map(s -> {
                    Map<String, String> customFieldMap = s.getCustomFields().stream()
                            .collect(Collectors.toMap(
                                    f -> f.getFieldName().toLowerCase(),
                                    CustomFields_supplier::getFieldValue,
                                    (existing, replacement) -> existing
                            ));

                    return SupplierResDTO.builder()
                            .supplierId(s.getSupplierId())
                            .name(s.getName())
                            .address(s.getAddress())
                            .contactName(s.getContactName())
                            .contactNumber(s.getContactNumber())
                            .paymentTerms(s.getPaymentTerms())
                            .period(s.getPeriod())
                            .vatNumber(customFieldMap.getOrDefault("vat number", null))
                            .bankDetails(customFieldMap.getOrDefault("bank details", null))
                            .build();
                })
                .toList();
    }

    public List<StoreDTO> getStores() {
        List<StoreDTO> stores = new ArrayList<>();
        List<SubCompany> subCompanies = subComRepo.findAll();
        for(SubCompany subCompany : subCompanies){
            for (Store store : subCompany.getStores()) {
                stores.add(StoreDTO.builder()
                                .storeId(store.getStoreId())
                                .storeName(subCompany.getSubCompanyName())
                                .storeAddress(store.getStoreAddress())
                                .storeEmail(store.getStoreEmail())
                                .tel(store.getTel())
                                .mobile(store.getMobile())
                                .businessRegNumber(store.getBusinessRegNumber())
                                .build());
            }
        }
        return stores;
    }

    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTO.setCategoryName(category.getCategoryName());

            List<com.synapse.StockMGT.DTOs.CategotyDTO.BrandDTO> brandDTOList = new ArrayList<>();

            for (Brand brand : category.getBrands()) {
                com.synapse.StockMGT.DTOs.CategotyDTO.BrandDTO brandDTO = new com.synapse.StockMGT.DTOs.CategotyDTO.BrandDTO();
                brandDTO.setBrandId(brand.getBrandId());
                brandDTO.setBrandName(brand.getBrandName());

                // Populate ItemInfos
                List<ItemInfoDTO> itemInfoDTOList = new ArrayList<>();
                for (ItemInfo itemInfo : brand.getItemInfos()) {
                    ItemInfoDTO itemInfoDTO = new ItemInfoDTO();
                    itemInfoDTO.setInfoId(itemInfo.getInfoId());
                    itemInfoDTO.setItemCode(itemInfo.getItemCode());
                    itemInfoDTO.setItemDescription(itemInfo.getItemDescription());

                    // Populate Items
                    List<ItemDTO> itemDTOList = new ArrayList<>();
                    for (Item item : itemInfo.getItems()) {
                        ItemDTO itemDTO = new ItemDTO();
                        itemDTO.setItemCode(item.getItemInfo().getItemCode());
                        itemDTO.setItemCost(item.getCost());
                        itemDTO.setDealerPrice(item.getDealerPrice());
                        itemDTO.setRetailPrice(item.getRetailPrice());
                        itemDTO.setStockType(item.getStockType());

                        // Populate IDs safely
                        itemDTO.setCategoryId(category.getCategoryId());
                        itemDTO.setBrandId(brand.getBrandId());
                        itemDTO.setSupplierId(item.getSupplier() != null ? item.getSupplier().getSupplierId() : 0);
                        itemDTO.setStore(item.getStore() != null ? item.getStore().getStoreId() : 0);

                        // Populate GRN-related fields
                        if (item.getSupplierGRN() != null) {
                            SupplierGRN grn = item.getSupplierGRN();
                            itemDTO.setWarranty(grn.getWarranty());
                            itemDTO.setSellerWarranty(grn.getSellerWarranty());
                            itemDTO.setQuantity(grn.getQuantity());
                            itemDTO.setPaymentStatus(grn.getPaymentStatus());
                            itemDTO.setSupplierPayment(grn.getSupplierPayment());
                            itemDTO.setSupplierInvoiceNumber(grn.getSupplierInvoiceNumber());
                            itemDTO.setGrnDate(grn.getGrnDate() != null ? grn.getGrnDate().toString() : null);
                            itemDTO.setSerialNumbers(grn.getSerialNumberList() != null ?
                                    Arrays.asList(grn.getSerialNumberList().split(",")) : new ArrayList<>());
                            itemDTO.setHasSerialNumbers(grn.getSerialNumberList() != null && !grn.getSerialNumberList().isBlank());
                            itemDTO.setCustomFields(grn.getCustomFields());
                        }

                        itemDTOList.add(itemDTO);
                    }

                    itemInfoDTO.setItems(itemDTOList);
                    itemInfoDTOList.add(itemInfoDTO);
                }

                // Populate GRNs
                List<SupplierGRNDTO> grnDTOList = new ArrayList<>();
                for (SupplierGRN grn : brand.getSupplierGRNs()) {
                    SupplierGRNDTO grnDTO = new SupplierGRNDTO();
                    grnDTO.setCategoryName(grn.getCategoryName());
                    grnDTO.setBrandName(grn.getBrandName());
                    grnDTO.setProductDescription(grn.getProductDescription());
                    grnDTO.setWarranty(grn.getWarranty());
                    grnDTO.setQuantity(grn.getQuantity());
                    grnDTO.setCost(grn.getCost());
                    grnDTO.setDealerPrice(grn.getDealerPrice());
                    grnDTO.setRetailPrice(grn.getRetailPrice());
                    grnDTO.setSupplierPayment(grn.getSupplierPayment());
                    grnDTO.setPaymentStatus(grn.getPaymentStatus());
                    grnDTO.setSerialNumbers(grn.getSerialNumberList());
                    grnDTO.setStoreId(grn.getStore());
                    grnDTO.setItems(grn.getItems());
                    grnDTO.setGrnDate(grn.getGrnDate());
                    grnDTO.setSupplierInvoice(grn.getSupplierInvoiceNumber());

                    grnDTOList.add(grnDTO);
                }

                brandDTO.setItemInfos(itemInfoDTOList);
                brandDTO.setSupplierGRNs(grnDTOList);

                brandDTOList.add(brandDTO);
            }

            categoryDTO.setBrands(brandDTOList);
            categoriesDTO.add(categoryDTO);
        }

        return categoriesDTO;
    }

}
