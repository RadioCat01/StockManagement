package com.synapse.StockMGT.Configs;

import com.synapse.StockMGT.DTOs.FormDTOs.FieldDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class TemplatesInitializerConfig {

    private final Map<String, List<FieldDTO>> companyFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> subCompanyFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> storeFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> storeFrontFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> counterFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> scannerFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> posTerminalFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> drawerFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> customerFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> supplierFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> categoryFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> brandFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> itemInfoFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> itemFields = new HashMap<>();
    private final Map<String, List<FieldDTO>> jobNoteFields = new HashMap<>();

    public TemplatesInitializerConfig() {
        companyFields.put("Company", List.of(
                new FieldDTO(0,"companyName", "text","Company Name", true)
        ));

        subCompanyFields.put("SubCompany", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0,"subCompanyName", "text","Sub-Company Name", true)
        ));

        storeFields.put("Store", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeName", "text","Store Name", true)
        ));

        storeFrontFields.put("StoreFront", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeIds","selectStores","Store", true),
                new FieldDTO(0, "storeFrontName", "text", "Store Front Name", true)
        ));

        counterFields.put("Counter", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterType", "text", "Counter Type", true),
                new FieldDTO(0, "counterName", "text","Counter Name", true)
        ));

        scannerFields.put("Scanner", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "scannerName", "text", "Scanner Name", true),
                new FieldDTO(0, "scannerSerial", "text", "Serial Number", false)
        ));

        posTerminalFields.put("PosTerminal", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "posTerminalName", "text", "POS Terminal Name", true),
                new FieldDTO(0, "posTerminalDetails", "textarea", "POS Terminal Details", false)
        ));

        drawerFields.put("CashDrawer", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "drawerName", "text", "Drawer Name", true)
        ));

        customerFields.put("Customers", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "name", "text", "Customer Name", true),
                new FieldDTO(0, "phone", "text", "Phone Number", true),
                new FieldDTO(0, "address", "textarea", "Address", false)
        ));

        supplierFields.put("Supplier", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "name", "text", "Supplier Name", true),
                new FieldDTO(0, "address", "textarea", "Address", false),
                new FieldDTO(0, "contactNumber", "text", "Contact Number", false),
                new FieldDTO(0, "contactName", "text", "Contact Name", false),
                new FieldDTO(0, "paymentTerms", "text", "Payment Terms", false),
                new FieldDTO(0, "period", "text", "Period", false)
        ));

        categoryFields.put("Category", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "categoryName", "text", "Category Name", true)
        ));

        brandFields.put("Brand", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "categoryId", "selectCategory", "Category", true),
                new FieldDTO(0, "brandName", "text", "Brand Name", true)
        ));

        itemInfoFields.put("ItemInfo", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "brandId", "selectBrand", "Brand", true),
                new FieldDTO(0, "itemCode", "text", "Item Code", true),
                new FieldDTO(0, "itemDescription", "textarea", "Item Description", false)
        ));

        itemFields.put("Item", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "storeId", "selectStore", "Store", true),
                new FieldDTO(0, "itemInfoId", "selectItemInfo", "Item Info", true),
                new FieldDTO(0, "supplierId", "selectSupplier", "Supplier", true),
                new FieldDTO(0, "serialNumber", "text", "Serial Number", true),
                new FieldDTO(0, "cost", "number", "Cost Price", true),
                new FieldDTO(0, "dealerPrice", "number", "Dealer Price", true),
                new FieldDTO(0, "retailPrice", "number", "Retail Price", true),
                new FieldDTO(0, "stockType", "text", "Stock Type", false),
                new FieldDTO(0, "currentPosition", "text", "Current Position", false),
                new FieldDTO(0, "lastUpdate", "date", "Last Updated Date", false)
        ));

        jobNoteFields.put("JobNotes", List.of(
                new FieldDTO(0, "companyId", "selectCompany", "Company", true),
                new FieldDTO(0, "subCompanyId", "selectSubCompany", "Sub Company", true),
                new FieldDTO(0, "jobNumber", "text", "Job Number", true),
                new FieldDTO(0, "jobDate", "date", "Job Date", true),
                new FieldDTO(0, "jobType", "selectJobType", "Job Type", true),
                new FieldDTO(0, "invoicedDate", "date", "Invoiced Date", false),
                new FieldDTO(0, "invoiceNumber", "text", "Invoice Number", true),
                new FieldDTO(0, "status", "selectJobStatus", "Job Status", true),
                new FieldDTO(0, "customerName", "text", "Customer Name", true),
                new FieldDTO(0, "customerPhone", "text", "Customer Phone", true)
        ));

    }
}

