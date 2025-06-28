package com.synapse.StockMGT.Configs;

import com.synapse.StockMGT.CustomFields.*;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TemplateDataInitializerService {

    private final TemplatesInitializerConfig templatesConfig;
    private final Company_FieldsRepo companyFieldsRepo;
    private final SubCom_FieldsRepo subCompanyFieldsRepo;
    private final Store_FieldRepo storeFieldRepo;
    private final StoreFront_FieldRepo storeFrontFieldRepo;
    private final Counter_FieldsRepo counterFieldsRepo;
    private final Scanner_FieldsRepo scannerFieldsRepo;
    private final POS_FieldRepo posFieldRepo;
    private final Drawer_FieldRepo drawerFieldRepo;
    private final Customer_FieldsRepo customerFieldsRepo;
    private final Supplier_FieldsRepo supplierFieldsRepo;
    private final Category_FieldsRepo categoryFieldsRepo;
    private final Brand_FieldsRepo brandFieldsRepo;
    private final ItemInfo_FieldsRepo itemInfoFieldsRepo;
    private final Item_FieldsRepo itemFieldsRepo;
    private final JobNote_FieldsRepo jobNoteFieldsRepo;

    @PostConstruct
    public void initializeAllCustomFields() {
        initializeCompanyFields();
        initializeSubCompanyFields();
        initializeStoreFields();
        initializeStoreFrontFields();
        initializeCounterFields();
        initializeScannerFields();
        initializePOSTerminalFields();
        initializeDrawerFields();
        initializeCustomerFields();
        initializeSupplierFields();
        initializeCategoryFields();
        initializeBrandFields();
        initializeItemInfoFields();
        initializeItemFields();
        initializeJobNotesFields();
    }

    private void initializeCompanyFields() {
        if (companyFieldsRepo.count() == 0) {
            templatesConfig.getCompanyFields().get("Company").forEach(field ->
                    companyFieldsRepo.save(Company_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeSubCompanyFields() {
        if (subCompanyFieldsRepo.count() == 0) {
            templatesConfig.getSubCompanyFields().get("SubCompany").forEach(field ->
                    subCompanyFieldsRepo.save(Subcompany_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeStoreFields() {
        if (storeFieldRepo.count() == 0) {
            templatesConfig.getStoreFields().get("Store").forEach(field ->
                    storeFieldRepo.save(Store_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeStoreFrontFields() {
        if (storeFrontFieldRepo.count() == 0) {
            templatesConfig.getStoreFrontFields().get("StoreFront").forEach(field ->
                    storeFrontFieldRepo.save(StoreFront_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeCounterFields() {
        if (counterFieldsRepo.count() == 0) {
            templatesConfig.getCounterFields().get("Counter").forEach(field ->
                    counterFieldsRepo.save(Counter_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeScannerFields() {
        if (scannerFieldsRepo.count() == 0) {
            templatesConfig.getScannerFields().get("Scanner").forEach(field ->
                    scannerFieldsRepo.save(Scanner_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializePOSTerminalFields() {
        if (posFieldRepo.count() == 0) {
            templatesConfig.getPosTerminalFields().get("PosTerminal").forEach(field ->
                    posFieldRepo.save(POS_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeDrawerFields() {
        if (drawerFieldRepo.count() == 0) {
            templatesConfig.getDrawerFields().get("CashDrawer").forEach(field ->
                    drawerFieldRepo.save(Drawer_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeCustomerFields() {
        if (customerFieldsRepo.count() == 0) {
            templatesConfig.getCustomerFields().get("Customers").forEach(field ->
                    customerFieldsRepo.save(Customer_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }
    private void initializeSupplierFields() {
        if (supplierFieldsRepo.count() == 0) {
            templatesConfig.getSupplierFields().get("Supplier").forEach(field ->
                    supplierFieldsRepo.save(Supplier_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeCategoryFields() {
        if (categoryFieldsRepo.count() == 0) {
            templatesConfig.getCategoryFields().get("Category").forEach(field ->
                    categoryFieldsRepo.save(Category_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }
    private void initializeBrandFields() {
        if (brandFieldsRepo.count() == 0) {
            templatesConfig.getBrandFields().get("Brand").forEach(field ->
                    brandFieldsRepo.save(Brand_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeItemInfoFields() {
        if (itemInfoFieldsRepo.count() == 0) {
            templatesConfig.getItemInfoFields().get("ItemInfo").forEach(field ->
                    itemInfoFieldsRepo.save(ItemInfo_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeItemFields() {
        if (itemFieldsRepo.count() == 0) {
            templatesConfig.getItemFields().get("Item").forEach(field ->
                    itemFieldsRepo.save(Item_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

    private void initializeJobNotesFields() {
        if (jobNoteFieldsRepo.count() == 0) {
            templatesConfig.getJobNoteFields().get("JobNotes").forEach(field ->
                    jobNoteFieldsRepo.save(JobNote_Fields.builder()
                            .fieldName(field.getFieldName())
                            .fieldType(field.getFieldType())
                            .fieldQuestion(field.getFieldQuestion())
                            .isMandatory(field.isMandatory())
                            .build()));
        }
    }

}

