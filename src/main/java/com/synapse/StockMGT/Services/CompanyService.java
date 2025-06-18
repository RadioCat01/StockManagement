package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.CompanyDTOs.*;
import com.synapse.StockMGT.DTOs.FormDTOs.DataReqDTO;
import com.synapse.StockMGT.DTOs.FormDTOs.GenericEntityDTO;
import com.synapse.StockMGT.Enums.CounterType;
import com.synapse.StockMGT.Models.CompanyHierarchy.*;
import com.synapse.StockMGT.Models.CustomFields.FieldData;
import com.synapse.StockMGT.Models.CustomFields.TemplateFields;
import com.synapse.StockMGT.Models.CustomFields.Templates;
import com.synapse.StockMGT.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
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
    private final TemplateRepo templateRepo;
    private final FieldDataRepo fieldDataRepo;

    public Company createCompany(Map<String, Object> formData) {
        Company company = Company.builder()
                .companyName((String) formData.get("companyName"))
                .build();
        companyRepo.save(company);

        Templates template = templateRepo.findByTemplateType("Company")
                .orElseThrow();

        List<TemplateFields> fields = template.getTemplateFields();
        for (TemplateFields field : fields) {
            if (formData.containsKey(field.getFieldName())) {
                FieldData data = FieldData.builder()
                        .template(template)
                        .customField(field)
                        .entityId(String.valueOf(company.getCompanyId()))
                        .formType("Company")
                        .fieldType(field.getFieldType())
                        .fieldValue(String.valueOf(formData.get(field.getFieldName())))
                        .build();
                fieldDataRepo.save(data);
            }
        }
        return company;
    }

    public SubCompany createSubCompany(Map<String, Object> formData) {
        Company company = companyRepo.findById(
                Integer.parseInt((String) formData.get("companyId"))
        ).orElseThrow(() -> new RuntimeException("Company Not Found"));


        SubCompany subCompany = SubCompany.builder()
                .subCompanyName((String) formData.get("subCompanyName"))
                .company(company)
                .build();

        subCompanyRepo.save(subCompany);
        company.getSubCompanies().add(subCompany);
        companyRepo.save(company);

        Templates template = templateRepo.findByTemplateType("SubCompany")
                .orElseThrow();
        saveCustomFields(String.valueOf(subCompany.getSubCompanyId()), "SubCompany", template, formData);

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
        Templates template = templateRepo.findByTemplateType("Store").orElseThrow();
        saveCustomFields(String.valueOf(store.getStoreId()), "Store", template, formData);

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

        Templates template = templateRepo.findByTemplateType("StoreFront").orElseThrow();
        saveCustomFields(String.valueOf(storeFront.getStorefrontId()), "StoreFront", template, formData);

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

        company.getCounters().add(counter);
        subCompany.getCounters().add(counter);
        storeFront.getCounter().add(counter);

        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        storeFrontRepo.save(storeFront);


        Templates template = templateRepo.findByTemplateType("Counter").orElseThrow();
        saveCustomFields(String.valueOf(counter.getCounterId()), "Counter", template, formData);


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

        Templates template = templateRepo.findByTemplateType("Scanner").orElseThrow();
        saveCustomFields(String.valueOf(scanner.getScannerId()), "Scanner", template, formData);


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

        Templates template = templateRepo.findByTemplateType("POSTerminal").orElseThrow();
        saveCustomFields(String.valueOf(terminal.getPosTerminalId()), "POSTerminal", template, formData);

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

        Templates template = templateRepo.findByTemplateType("CashDrawer").orElseThrow();
        saveCustomFields(String.valueOf(cashDrawer.getDrawerId()), "Drawer", template, formData);

        return cashDrawer;
    }


    private void saveCustomFields(String entityId, String formType, Templates template, Map<String, Object> formData) {
        List<TemplateFields> fields = template.getTemplateFields();
        for (TemplateFields field : fields) {
            FieldData data = FieldData.builder()
                    .template(template)
                    .customField(field)
                    .entityId(entityId)
                    .formType(formType)
                    .fieldType(field.getFieldType())
                    .build();
            if (formData.containsKey(field.getFieldName())) {
                switch (field.getFieldType()) {
                    case "selectCompany" -> {
                        int id = Integer.parseInt(formData.get(field.getFieldName()).toString());
                        Company company = companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company Not Found"));
                        data.setFieldValue(company.getCompanyName());
                    }
                    case "selectSubCompany" -> {
                        int id = Integer.parseInt(formData.get(field.getFieldName()).toString());
                        SubCompany subCompany = subCompanyRepo.findById(id).orElseThrow(() -> new RuntimeException("SubCompany Not Found"));
                        data.setFieldValue(subCompany.getSubCompanyName());
                    }
                    case "selectStore" -> {
                        int id = Integer.parseInt(formData.get(field.getFieldName()).toString());
                        Store store = storeRepo.findById(id).orElseThrow(() -> new RuntimeException("Store Not Found"));
                        data.setFieldValue(store.getStoreName());
                    }
                    case "selectStores" -> {
                        Object value = formData.get(field.getFieldName());
                        List<Integer> ids = ((List<?>) value).stream()
                                .map(String::valueOf)
                                .map(Integer::parseInt)
                                .toList();

                        List<Store> stores = storeRepo.findAllById(ids);
                        data.setFieldValue(stores.stream()
                                .map(Store::getStoreName)
                                .collect(Collectors.joining(", ")));
                    }
                    case "selectStoreFront" -> {
                        int id = Integer.parseInt(formData.get(field.getFieldName()).toString());
                        StoreFront front = storeFrontRepo.findById(id).orElseThrow(() -> new RuntimeException("Store Front Not Found"));
                        data.setFieldValue(front.getStoreFrontName());
                    }
                    case "selectStoreCounter" -> {
                        int id = Integer.parseInt(formData.get(field.getFieldName()).toString());
                        Counter counter = counterRepo.findById(id).orElseThrow(() -> new RuntimeException("Counter Not Found"));
                        data.setFieldValue(counter.getCounterName());
                    }
                    default -> data.setFieldValue(String.valueOf(formData.get(field.getFieldName())));
                }

                fieldDataRepo.save(data);
            }
        }
    }



    public List<GenericEntityDTO> getAllCompanies() {
        return convertToGenericDTO(
                companyRepo.findAll(),
                "Company",
                company -> String.valueOf(company.getCompanyId()),
                Company::getCompanyName,
                (store, dto) -> {}
        );
    }


    public List<GenericEntityDTO> getAllSubCompanies() {
        return convertToGenericDTO(
                subCompanyRepo.findAll(),
                "SubCompany",
                sub -> String.valueOf(sub.getSubCompanyId()),
                SubCompany::getSubCompanyName,
                (store, dto) -> {
                    dto.setCompanyId(store.getSubCompanyId());
                }
        );
    }

    public List<GenericEntityDTO> getAllStores() {
        return convertToGenericDTO(
                storeRepo.findAll(),
                "Store",
                store -> String.valueOf(store.getStoreId()),
                Store::getStoreName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                }
        );

    }

    public List<GenericEntityDTO> getAllStoreFronts() {
        return convertToGenericDTO(
                storeFrontRepo.findAll(),
                "StoreFront",
                front -> String.valueOf(front.getStorefrontId()),
                StoreFront::getStoreFrontName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                    dto.setStoreId(store.getStore().stream().map(Store::getStoreId).toList());
                }
        );
    }

    public List<GenericEntityDTO> getAllCounters() {
        return convertToGenericDTO(
                counterRepo.findAll(),
                "Counter",
                counter -> String.valueOf(counter.getCounterId()),
                Counter::getCounterName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                    dto.setStorefrontId(store.getStoreFront().getStorefrontId());
                }
        );
    }

    public List<GenericEntityDTO> getAllScanners() {
        return convertToGenericDTO(
                scannerRepo.findAll(),
                "Scanner",
                scanner -> String.valueOf(scanner.getScannerId()),
                Scanner::getScannerName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                    dto.setCounterId(store.getCounter().getCounterId());
                }
        );
    }

    public List<GenericEntityDTO> getAllPOSTerminals() {
        return convertToGenericDTO(
                posTerminalRepo.findAll(),
                "POSTerminal",
                pos -> String.valueOf(pos.getPosTerminalId()),
                PosTerminal::getPosTerminalName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                    dto.setCounterId(store.getCounter().getCounterId());
                }
        );
    }

    public List<GenericEntityDTO> getAllDrawers() {
        return convertToGenericDTO(
                cashDrawerRepo.findAll(),
                "CashDrawer",
                drawer -> String.valueOf(drawer.getDrawerId()),
                CashDrawer::getDrawerName,
                (store, dto) -> {
                    dto.setCompanyId(store.getCompany().getCompanyId());
                    dto.setSubcompanyId(store.getSubCompany().getSubCompanyId());
                    dto.setCounterId(store.getCounter().getCounterId());
                }
        );
    }

    private <T> List<GenericEntityDTO> convertToGenericDTO(
            List<T> entities,
            String templateType,
            Function<T, String> getId,
            Function<T, String> getDisplayName,
            BiConsumer<T, GenericEntityDTO> populateFields
    ) {
        Templates template = templateRepo.findByTemplateType(templateType)
                .orElseThrow(() -> new RuntimeException(templateType + " template not found"));

        List<FieldData> allFieldData = fieldDataRepo.findByTemplate(template);

        return entities.stream().map(entity -> {
            String entityId = getId.apply(entity);
            String displayName = getDisplayName.apply(entity);
            Map<String, String> customFieldsMap = allFieldData.stream()
                    .filter(fd -> entityId.equals(fd.getEntityId()))
                    .collect(Collectors.toMap(
                            fd -> fd.getCustomField().getFieldQuestion(),
                            FieldData::getFieldValue
                    ));

            customFieldsMap.entrySet().removeIf(entry ->
                    entry.getValue() != null && entry.getValue().equals(displayName));

            GenericEntityDTO dto = GenericEntityDTO.builder()
                    .entityId(entityId)
                    .displayName(displayName)
                    .customFields(customFieldsMap)
                    .build();
            populateFields.accept(entity, dto);
            return dto;
        }).collect(Collectors.toList());
    }


}
