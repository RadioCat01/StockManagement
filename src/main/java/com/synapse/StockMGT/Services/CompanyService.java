package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.CustomFields.*;
import com.synapse.StockMGT.DTOs.FormDTOs.GenericEntityDTO;
import com.synapse.StockMGT.Enums.CounterType;
import com.synapse.StockMGT.Models.CompanyHierarchy.*;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final SubCompanyRepo subCompanyRepo;
    private final StoreRepo storeRepo;
    private final StoreFrontRepo storeFrontRepo;
    private final CounterRepo counterRepo;
    private final PosTerminalRepo posTerminalRepo;
    private final CashDrawerRepo cashDrawerRepo;
    private final ScannerRepo scannerRepo;

    private final Company_FieldsRepo  company_FieldsRepo;
    private final Company_DataRepo company_DataRepo;
    private final SubCom_FieldsRepo subCom_FieldsRepo;
    private final SubCom_DataRepo subCom_DataRepo;
    private final Store_FieldRepo storeFieldRepo;
    private final Store_DataRepo storeDataRepo;
    private final StoreFront_FieldRepo storeFrontFieldRepo;
    private final StoreFront_DataRepo storeFrontDataRepo;
    private final Counter_FieldsRepo counter_FieldsRepo;
    private final Counter_DataRepo counter_DataRepo;
    private final Scanner_FieldsRepo scanner_FieldsRepo;
    private final Scanner_DataRepo scanner_DataRepo;
    private final POS_FieldRepo posFieldRepo;
    private final POS_DataRepo posDataRepo;
    private final Drawer_FieldRepo drawer_FieldsRepo;
    private final Drawer_DataRepo drawer_DataRepo;

    public Company createCompany(Map<String, Object> formData) {
        Company company = companyRepo.save(Company.builder()
                .companyName((String) formData.get("companyName"))
                .build());

        List<Company_Fields> fields = company_FieldsRepo.findAll();
        List<Company_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> Company_Data.builder()
                        .field(field)
                        .entityId(String.valueOf(company.getCompanyId()))
                        .formType("Company")
                        .fieldType(field.getFieldType())
                        .fieldValue(String.valueOf(formData.get(field.getFieldName())))
                        .company(company)
                        .build())
                .toList();
        company_DataRepo.saveAll(dataToSave);
        return company;
    }

    public SubCompany createSubCompany(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt((String) formData.get("companyId")))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));

        SubCompany subCompany = subCompanyRepo.save(SubCompany.builder()
                .subCompanyName((String) formData.get("subCompanyName"))
                .company(company)
                .build());
        company.getSubCompanies().add(subCompany);
        companyRepo.save(company);

        List<Subcompany_Fields> fields = subCom_FieldsRepo.findAll();

        List<SubCompany_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    if ("companyId".equals(field.getFieldName())) {
                        fieldValue = company.getCompanyName();
                    } else {
                        fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return SubCompany_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(subCompany.getSubCompanyId()))
                            .formType("SubCompany")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .subCompany(subCompany)
                            .build();
                })
                .toList();

        subCom_DataRepo.saveAll(dataToSave);

        return subCompany;
    }

    public Store createStore(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));

        Store store = Store.builder()
                .storeName((String) formData.get("storeName"))
                .company(company)
                .subCompany(subCompany)
                .build();

        storeRepo.save(store);

        company.getStores().add(store);
        subCompany.getStores().add(store);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);

        List<Store_Fields> fields = storeFieldRepo.findAll();
        List<Store_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return Store_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(store.getStoreId()))
                            .formType("Store")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .store(store)
                            .build();
                })
                .toList();

        storeDataRepo.saveAll(dataToSave);

        return store;
    }



    public StoreFront createStoreFront(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));

        List<String> storeIdStrings = (List<String>) formData.get("storeIds");
        List<Integer> storeIds = storeIdStrings.stream()
                .map(Integer::parseInt)
                .toList();

        List<Store> stores = storeIds.stream()
                .map(id -> storeRepo.findById(id).orElseThrow(() -> new RuntimeException("Store Not Found")))
                .toList();

        StoreFront storeFront = StoreFront.builder()
                .storeFrontName((String) formData.get("storeFrontName"))
                .company(company)
                .subCompany(subCompany)
                .store(new ArrayList<>())
                .build();

        stores.forEach(store -> {
            storeFront.getStore().add(store);
            store.getStoreFronts().add(storeFront);
        });

        company.getStoreFronts().add(storeFront);
        subCompany.getStoreFronts().add(storeFront);

        storeFrontRepo.save(storeFront);
        storeRepo.saveAll(stores);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);

        List<StoreFront_Fields> fields = storeFrontFieldRepo.findAll();
        List<StoreFront_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        case "storeIds" ->
                                fieldValue = stores.stream()
                                        .map(Store::getStoreName)
                                        .collect(Collectors.joining(", "));
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }
                    return StoreFront_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(storeFront.getStorefrontId()))
                            .formType("StoreFront")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .storeFront(storeFront)
                            .build();
                })
                .toList();
        storeFrontDataRepo.saveAll(dataToSave);

        return storeFront;
    }



    public Counter createCounter(Map<String, Object> formData) {
        StoreFront storeFront = storeFrontRepo.findById(Integer.parseInt(formData.get("storeFrontId").toString()))
                .orElseThrow(() -> new RuntimeException("Store Front Not Found"));
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));

        Counter counter = Counter.builder()
                .counterType(CounterType.valueOf((String) formData.get("counterType")))
                .counterName((String) formData.get("counterName"))
                .company(company)
                .subCompany(subCompany)
                .storeFront(storeFront)
                .build();

        counterRepo.save(counter);
        company.getCounters().add(counter);
        subCompany.getCounters().add(counter);
        storeFront.getCounter().add(counter);

        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        storeFrontRepo.save(storeFront);

        List<Counter_Fields> fields = counter_FieldsRepo.findAll();
        List<Counter_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        case "storeFrontId" -> fieldValue = storeFront.getStoreFrontName();
//                        case "storeId" -> fieldValue =
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return Counter_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(counter.getCounterId()))
                            .formType("Counter")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .counter(counter)
                            .build();
                })
                .toList();

        counter_DataRepo.saveAll(dataToSave);
        return counter;
    }


    public Scanner createScanner(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(Integer.parseInt(formData.get("counterId").toString()))
                .orElseThrow(() -> new RuntimeException("Counter Not Found"));

        Scanner scanner = Scanner.builder()
                .scannerName((String) formData.get("scannerName"))
                .scannerSerial((String) formData.get("scannerSerial"))
                .company(company)
                .subCompany(subCompany)
                .counter(counter)
                .build();

        scannerRepo.save(scanner);
        company.getScanners().add(scanner);
        subCompany.getScanners().add(scanner);
        counter.getScanners().add(scanner);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);

        List<Scanner_Fields> fields = scanner_FieldsRepo.findAll();
        List<Scanner_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        case "counterId" -> fieldValue = counter.getCounterName();
                        case "storeFrontId" -> fieldValue = counter.getStoreFront().getStoreFrontName();
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return Scanner_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(scanner.getScannerId()))
                            .formType("Scanner")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .scanner(scanner)
                            .build();
                })
                .toList();

        scanner_DataRepo.saveAll(dataToSave);
        return scanner;
    }



    public PosTerminal createPOSTerminal(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(Integer.parseInt(formData.get("counterId").toString()))
                .orElseThrow(() -> new RuntimeException("Counter Not Found"));

        PosTerminal terminal = PosTerminal.builder()
                .posTerminalName((String) formData.get("posTerminalName"))
                .posTerminalDetails((String) formData.get("posTerminalDetails"))
                .company(company)
                .subCompany(subCompany)
                .counter(counter)
                .build();

        posTerminalRepo.save(terminal);
        company.getPosTerminals().add(terminal);
        subCompany.getPosTerminals().add(terminal);
        counter.getPosTerminals().add(terminal);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);

        List<POS_Fields> fields = posFieldRepo.findAll();
        List<POS_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        case "counterId" -> fieldValue = counter.getCounterName();
                        case "storeFrontId" -> fieldValue = counter.getStoreFront().getStoreFrontName();
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return POS_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(terminal.getPosTerminalId()))
                            .formType("POSTerminal")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .posTerminal(terminal)
                            .build();
                })
                .toList();

        posDataRepo.saveAll(dataToSave);
        return terminal;
    }



    public CashDrawer createDrawer(Map<String, Object> formData) {
        Company company = companyRepo.findById(Integer.parseInt(formData.get("companyId").toString()))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(Integer.parseInt(formData.get("subCompanyId").toString()))
                .orElseThrow(() -> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(Integer.parseInt(formData.get("counterId").toString()))
                .orElseThrow(() -> new RuntimeException("Counter Not Found"));

        CashDrawer cashDrawer = CashDrawer.builder()
                .drawerName((String) formData.get("drawerName"))
                .company(company)
                .subCompany(subCompany)
                .counter(counter)
                .build();

        cashDrawerRepo.save(cashDrawer);
        company.getCashDrawers().add(cashDrawer);
        subCompany.getCashDrawers().add(cashDrawer);
        counter.getCashDrawers().add(cashDrawer);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);

        List<Drawer_Fields> fields = drawer_FieldsRepo.findAll();
        List<Drawer_Data> dataToSave = fields.stream()
                .filter(field -> formData.containsKey(field.getFieldName()))
                .map(field -> {
                    String fieldValue;
                    switch (field.getFieldName()) {
                        case "companyId" -> fieldValue = company.getCompanyName();
                        case "subCompanyId" -> fieldValue = subCompany.getSubCompanyName();
                        case "counterId" -> fieldValue = counter.getCounterName();
                        case "storeFrontId" -> fieldValue = counter.getStoreFront().getStoreFrontName();
                        default -> fieldValue = String.valueOf(formData.get(field.getFieldName()));
                    }

                    return Drawer_Data.builder()
                            .field(field)
                            .entityId(String.valueOf(cashDrawer.getDrawerId()))
                            .formType("CashDrawer")
                            .fieldType(field.getFieldType())
                            .fieldValue(fieldValue)
                            .drawer(cashDrawer)
                            .build();
                })
                .toList();

        drawer_DataRepo.saveAll(dataToSave);
        return cashDrawer;
    }


    public List<GenericEntityDTO> getAllCompanies() {
        return companyRepo.findAll().stream().map(company -> {
            Map<String, String> customFields = company.getData().stream()
                    .filter(d -> d.getField() != null && !"companyName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            Company_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(company.getCompanyId()))
                    .companyId(company.getCompanyId())
                    .displayName(company.getCompanyName())
                    .customFields(customFields)
                    .build();

        }).toList();
    }

    public List<GenericEntityDTO> getAllSubCompanies() {
        return subCompanyRepo.findAll().stream().map(sub -> {
            Map<String, String> customFields = sub.getData().stream()
                    .filter(d -> d.getField() != null && !"subCompanyName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            SubCompany_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(sub.getSubCompanyId()))
                    .companyId(sub.getCompany().getCompanyId())
                    .subcompanyId(sub.getSubCompanyId())
                    .displayName(sub.getSubCompanyName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }

    public List<GenericEntityDTO> getAllStores() {
        return storeRepo.findAll().stream().map(store -> {
            Map<String, String> customFields = store.getData().stream()
                    .filter(d -> d.getField() != null && !"storeName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            Store_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(store.getStoreId()))
                    .companyId(store.getCompany().getCompanyId())
                    .subcompanyId(store.getSubCompany().getSubCompanyId())
                    .displayName(store.getStoreName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }

    public List<GenericEntityDTO> getAllStoreFronts() {
        return storeFrontRepo.findAll().stream().map(front -> {
            Map<String, String> customFields = front.getData().stream()
                    .filter(d -> d.getField() != null && !"storeFrontName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            StoreFront_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(front.getStorefrontId()))
                    .companyId(front.getCompany().getCompanyId())
                    .subcompanyId(front.getSubCompany().getSubCompanyId())
                    .storeId(front.getStore().stream().map(Store::getStoreId).toList())
                    .displayName(front.getStoreFrontName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }


    public List<GenericEntityDTO> getAllCounters() {
        return counterRepo.findAll().stream().map(counter -> {
            Map<String, String> customFields = counter.getData().stream()
                    .filter(d -> d.getField() != null && !"counterName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            Counter_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(counter.getCounterId()))
                    .companyId(counter.getCompany().getCompanyId())
                    .subcompanyId(counter.getSubCompany().getSubCompanyId())
                    .storefrontId(counter.getStoreFront().getStorefrontId())
                    .displayName(counter.getCounterName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }


    public List<GenericEntityDTO> getAllScanners() {
        return scannerRepo.findAll().stream().map(scanner -> {
            Map<String, String> customFields = scanner.getData().stream()
                    .filter(d -> d.getField() != null && !"scannerName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            Scanner_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(scanner.getScannerId()))
                    .companyId(scanner.getCompany().getCompanyId())
                    .subcompanyId(scanner.getSubCompany().getSubCompanyId())
                    .counterId(scanner.getCounter().getCounterId())
                    .displayName(scanner.getScannerName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }


    public List<GenericEntityDTO> getAllPOSTerminals() {
        return posTerminalRepo.findAll().stream().map(pos -> {
            Map<String, String> customFields = pos.getData().stream()
                    .filter(d -> d.getField() != null && !"posTerminalName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            POS_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(pos.getPosTerminalId()))
                    .companyId(pos.getCompany().getCompanyId())
                    .subcompanyId(pos.getSubCompany().getSubCompanyId())
                    .counterId(pos.getCounter().getCounterId())
                    .displayName(pos.getPosTerminalName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }


    public List<GenericEntityDTO> getAllDrawers() {
        return cashDrawerRepo.findAll().stream().map(drawer -> {
            Map<String, String> customFields = drawer.getData().stream()
                    .filter(d -> d.getField() != null && !"drawerName".equals(d.getField().getFieldName()))
                    .collect(Collectors.toMap(
                            data -> data.getField().getFieldQuestion(),
                            Drawer_Data::getFieldValue
                    ));

            return GenericEntityDTO.builder()
                    .entityId(String.valueOf(drawer.getDrawerId()))
                    .companyId(drawer.getCompany().getCompanyId())
                    .subcompanyId(drawer.getSubCompany().getSubCompanyId())
                    .counterId(drawer.getCounter().getCounterId())
                    .displayName(drawer.getDrawerName())
                    .customFields(customFields)
                    .build();
        }).toList();
    }
}
