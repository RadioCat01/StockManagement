package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.*;
import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {
    private final CompanyRepo companyRepo;
    private final Company_FieldsRepo  companyFieldsRepo;
    private final SubCom_FieldsRepo subComFieldsRepo;
    private final Store_FieldRepo  storeFieldsRepo;
    private final StoreFront_FieldRepo storeFrontFieldsRepo;
    private final Counter_FieldsRepo counterFieldsRepo;
    private final Scanner_FieldsRepo scannerFieldsRepo;
    private final POS_FieldRepo posFieldsRepo;
    private final Drawer_FieldRepo  drawerFieldsRepo;

    private final Customer_FieldsRepo customerFieldsRepo;
    private final Supplier_FieldsRepo supplierFieldsRepo;
    private final Category_FieldsRepo categoryFieldsRepo;
    private final Brand_FieldsRepo brandFieldsRepo;
    private final ItemInfo_FieldsRepo itemInfoFieldsRepo;
    private final Item_FieldsRepo itemFieldsRepo;
    private final JobNote_FieldsRepo jobNotesFieldRepo;

    public List<FieldDTO> getCompanyFields() {
        return companyFieldsRepo.findAll().stream().map(com -> FieldDTO.builder()
                .fieldName(com.getFieldName())
                .fieldType(com.getFieldType())
                .fieldQuestion(com.getFieldQuestion())
                .isMandatory(com.getIsMandatory())
                .build()).toList();
    }

    public FieldDTO addCompanyField(FieldDTO fieldDTO) {
        companyFieldsRepo.save(Company_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }

    public List<FieldDTO> getSubCompanyFields() {
        return subComFieldsRepo.findAll().stream()
                .map(field -> FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build())
                .toList();
    }

    public FieldDTO addSubCompanyField(FieldDTO fieldDTO) {
        subComFieldsRepo.save(Subcompany_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());

        return fieldDTO;
    }

    public List<FieldDTO> getStoreFields() {
        return storeFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addStoreField(FieldDTO fieldDTO) {
        storeFieldsRepo.save(Store_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getStorefrontFields() {
        return storeFrontFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addStorefrontField(FieldDTO fieldDTO) {
        storeFrontFieldsRepo.save(StoreFront_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getCounterFields() {
        return counterFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addCounterField(FieldDTO fieldDTO) {
        counterFieldsRepo.save(Counter_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getScannerFields() {
        return scannerFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addScannerField(FieldDTO fieldDTO) {
        scannerFieldsRepo.save(Scanner_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getPOSTerminalFields() {
        return posFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addPOSTerminalField(FieldDTO fieldDTO) {
        posFieldsRepo.save(POS_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getCashDrawerFields() {
        return drawerFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addCashDrawerField(FieldDTO fieldDTO) {
        drawerFieldsRepo.save(Drawer_Fields.builder()
                .fieldName(fieldDTO.getFieldName())
                .fieldType(fieldDTO.getFieldType())
                .fieldQuestion(fieldDTO.getFieldQuestion())
                .isMandatory(fieldDTO.isMandatory())
                .build());
        return fieldDTO;
    }

    public List<FieldDTO> getCustomerFields() {
        return customerFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()).toList();
    }

    public FieldDTO addCustomerField(FieldDTO fieldDTO) {
        customerFieldsRepo.save(
                Customer_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }

    public List<FieldDTO> getSupplierFields() {
        return supplierFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()).toList();
    }

    public FieldDTO addSupplierField(FieldDTO fieldDTO) {
        supplierFieldsRepo.save(
                Supplier_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }

    public List<FieldDTO> getCategoryFields() {
        return categoryFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()).toList();
    }

    public FieldDTO addCategoryField(FieldDTO fieldDTO) {
        categoryFieldsRepo.save(
                Category_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }

    public List<FieldDTO> getBrandFields() {
        return brandFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addBrandField(FieldDTO fieldDTO) {
        brandFieldsRepo.save(
                Brand_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build()
        );
        return fieldDTO;
    }

    public List<FieldDTO> getItemInfoFields() {
        return itemInfoFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addItemInfoField(FieldDTO fieldDTO) {
        itemInfoFieldsRepo.save(
                ItemInfo_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }
    public List<FieldDTO> getItemFields() {
        return itemFieldsRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addItemField(FieldDTO fieldDTO) {
        itemFieldsRepo.save(
                Item_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }

    public List<FieldDTO> getJobNotesFields() {
        return jobNotesFieldRepo.findAll().stream().map(field ->
                FieldDTO.builder()
                        .fieldName(field.getFieldName())
                        .fieldType(field.getFieldType())
                        .fieldQuestion(field.getFieldQuestion())
                        .isMandatory(field.getIsMandatory())
                        .build()
        ).toList();
    }

    public FieldDTO addJobNotesField(FieldDTO fieldDTO) {
        jobNotesFieldRepo.save(
                JobNote_Fields.builder()
                        .fieldName(fieldDTO.getFieldName())
                        .fieldType(fieldDTO.getFieldType())
                        .fieldQuestion(fieldDTO.getFieldQuestion())
                        .isMandatory(fieldDTO.isMandatory())
                        .build());
        return fieldDTO;
    }



}