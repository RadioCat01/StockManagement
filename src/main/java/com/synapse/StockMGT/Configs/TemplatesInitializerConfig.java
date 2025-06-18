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
    private final Map<String, List<FieldDTO>> defaultTemplates = new HashMap<>();

    public TemplatesInitializerConfig() {
        defaultTemplates.put("Company", List.of(
                new FieldDTO(0,"companyName", "text","Company Name", true)
        ));

        defaultTemplates.put("SubCompany", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0,"subCompanyName", "text","Sub-Company Name", true)
        ));

        defaultTemplates.put("Store", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeName", "text","Store Name", true)
        ));

        defaultTemplates.put("StoreFront", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeIds","selectStores","Store", true),
                new FieldDTO(0, "storeFrontName", "text", "Store Front Name", true)
        ));

        defaultTemplates.put("Counter", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterType", "text", "Counter Type", true),
                new FieldDTO(0, "counterName", "text","Counter Name", true)
        ));


        defaultTemplates.put("Scanner", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "scannerName", "text", "Scanner Name", true),
                new FieldDTO(0, "scannerSerial", "text", "Serial Number", false)
        ));


        defaultTemplates.put("PosTerminal", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "posTerminalName", "text", "POS Terminal Name", true),
                new FieldDTO(0, "posTerminalDetails", "textarea", "POS Terminal Details", false)
        ));

        defaultTemplates.put("CashDrawer", List.of(
                new FieldDTO(0,"companyId","selectCompany","Company",true),
                new FieldDTO(0, "subCompanyId","selectSubCompany","Sub Company", true ),
                new FieldDTO(0,"storeId","selectStore","Store", true),
                new FieldDTO(0, "storeFrontId","selectStoreFront","Store Front", true),
                new FieldDTO(0, "counterId","selectStoreCounter","Counter", true),
                new FieldDTO(0, "drawerName", "text", "Drawer Name", true)
        ));

    }
}
