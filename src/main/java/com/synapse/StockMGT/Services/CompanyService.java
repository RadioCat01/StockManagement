package com.synapse.StockMGT.Services;

import com.synapse.StockMGT.DTOs.CompanyDTOs.*;
import com.synapse.StockMGT.DTOs.FormDTOs.DataReqDTO;
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

    public SubCompanyDTO createSubCompany(SubCompanyDTO subCompanyDTO) {
        Company company = companyRepo.findById(subCompanyDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.save(SubCompany.builder()
                        .subCompanyName(subCompanyDTO.getSubCompanyName())
                        .subCompanyAddress(subCompanyDTO.getSubCompanyAddress())
                        .subCompanyPhone(subCompanyDTO.getSubCompanyPhone())
                        .subCompanyEmail(subCompanyDTO.getSubCompanyEmail())
                        .company(company)
                        .build());
        company.getSubCompanies().add(subCompany);
        companyRepo.save(company);
        return subCompanyDTO;
    }

    public StoreDTO createStore(StoreDTO storeDTO) {
        Company company = companyRepo.findById(storeDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(storeDTO.getSubCompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));

        Store store = storeRepo.save(Store.builder()
                        .storeAddress(storeDTO.getStoreAddress())
                        .storeEmail(storeDTO.getStoreEmail())
                        .tel(storeDTO.getTel())
                        .mobile(storeDTO.getMobile())
                        .businessRegNumber(storeDTO.getBusinessRegNumber())
                        .company(company)
                        .subCompany(subCompany)
                        .build());
        company.getStores().add(store);
        subCompany.getStores().add(store);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        return storeDTO;
    }

    public StoreFrontDTO createStoreFront(StoreFrontDTO storeFrontDTO) {
        Company company = companyRepo.findById(storeFrontDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(storeFrontDTO.getSubCompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));

        List<Store> stores = storeFrontDTO.getStoreIds().stream().map(id ->
                storeRepo.findById(id).orElseThrow()).toList();

        StoreFront storeFront = StoreFront.builder()
                .storeAddress(storeFrontDTO.getStoreAddress())
                .storeEmail(storeFrontDTO.getStoreEmail())
                .tel(storeFrontDTO.getTel())
                .mobile(storeFrontDTO.getMobile())
                .businessRegNumber(storeFrontDTO.getBusinessRegNumber())
                .company(company)
                .subCompany(subCompany)
                .store(new ArrayList<>())
                .build();

        for (Store store : stores) {
            storeFront.getStore().add(store);
            store.getStoreFronts().add(storeFront);
        }

        company.getStoreFronts().add(storeFront);
        subCompany.getStoreFronts().add(storeFront);

        storeFrontRepo.save(storeFront);

        storeRepo.saveAll(stores);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);

        return storeFrontDTO;
    }

    public CounterDTO createCounter(CounterDTO counterDTO) {
        StoreFront storeFront = storeFrontRepo.findById(counterDTO.getStoreFrontId())
                .orElseThrow(()-> new RuntimeException("Store Front Not Found"));
        Company company = companyRepo.findById(counterDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(counterDTO.getSubCompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));

        Counter counter = Counter.builder()
                .counterType(counterDTO.getCounterType())
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
        return counterDTO;
    }

    public ScannerDTO  createScanner(ScannerDTO scannerDTO) {
        Company company = companyRepo.findById(scannerDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(scannerDTO.getSubcompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(scannerDTO.getCounterId())
                .orElseThrow(()-> new RuntimeException("Counter Not Found"));

        Scanner scanner = scannerRepo.save(Scanner.builder()
                .scannerName(scannerDTO.getScannerName())
                .scannerSerial(scannerDTO.getScannerSerial())
                .company(company)
                .subCompany(subCompany)
                .counter(counter)
                .build());
        company.getScanners().add(scanner);
        subCompany.getScanners().add(scanner);
        counter.getScanners().add(scanner);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);
        return scannerDTO;
    }

    public POSTerminalDTO createPOSTerminal(POSTerminalDTO posterminalDTO) {
        Company company = companyRepo.findById(posterminalDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(posterminalDTO.getSubcompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(posterminalDTO.getCounterId())
                .orElseThrow(()-> new RuntimeException("Counter Not Found"));

        PosTerminal terminal = posTerminalRepo.save(PosTerminal.builder()
                .posTerminalName(posterminalDTO.getPosTerminalName())
                .posTerminalDetails(posterminalDTO.getPosTerminalDetails())
                .company(company)
                .subCompany(subCompany)
                .counter(counter)
                .build());
        company.getPosTerminals().add(terminal);
        subCompany.getPosTerminals().add(terminal);
        counter.getPosTerminals().add(terminal);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);
        return posterminalDTO;
    }

    public DrawerDTO  createDrawer(DrawerDTO drawerDTO) {
        Company company = companyRepo.findById(drawerDTO.getCompanyId())
                .orElseThrow(()-> new RuntimeException("Company Not Found"));
        SubCompany subCompany = subCompanyRepo.findById(drawerDTO.getSubCompanyId())
                .orElseThrow(()-> new RuntimeException("SubCompany Not Found"));
        Counter counter = counterRepo.findById(drawerDTO.getCounterId())
                .orElseThrow(()-> new RuntimeException("Counter Not Found"));

        CashDrawer cashDrawer = cashDrawerRepo.save(CashDrawer.builder()
                        .drawerName(drawerDTO.getDrawerName())
                        .drawerDescription(drawerDTO.getDrawerDescription())
                        .drawerType(drawerDTO.getDrawerType())
                        .drawerStatus(drawerDTO.getDrawerStatus())
                        .company(company)
                        .subCompany(subCompany)
                        .counter(counter)
                        .build());
        company.getCashDrawers().add(cashDrawer);
        subCompany.getCashDrawers().add(cashDrawer);
        counter.getCashDrawers().add(cashDrawer);
        companyRepo.save(company);
        subCompanyRepo.save(subCompany);
        counterRepo.save(counter);
        return drawerDTO;
    }


    public List<CompanyDTO> getAllCompanies() {
        Templates template = templateRepo.findByTemplateType("Company")
                .orElseThrow(() -> new RuntimeException("Company template not found"));

        List<FieldData> allFieldData = fieldDataRepo.findByTemplate(template);

        return companyRepo.findAll().stream().map(company -> {
            Map<String, String> customFieldsMap = allFieldData.stream()
                    .filter(fd -> String.valueOf(company.getCompanyId()).equals(fd.getEntityId()))
                    .collect(Collectors.toMap(
                            fd -> fd.getCustomField().getFieldName(),
                            FieldData::getFieldValue
                    ));

            return CompanyDTO.builder()
                    .companyId(company.getCompanyId())
                    .companyName(customFieldsMap.getOrDefault("companyName", company.getCompanyName()))
                    .customFields(customFieldsMap)
                    .build();
        }).collect(Collectors.toList());
    }


    public List<SubCompanyDTO> getAllSubCompanies() {
        return subCompanyRepo.findAll().stream().map(sub ->
                SubCompanyDTO.builder()
                        .subCompanyName(sub.getSubCompanyName())
                        .companyId(sub.getCompany().getCompanyId())
                        .subCompanyId(sub.getSubCompanyId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<StoreDTO> getAllStores() {
        return storeRepo.findAll().stream().map(store ->
                StoreDTO.builder()
                        .storeAddress(store.getStoreAddress())
                        .storeEmail(store.getStoreEmail())
                        .tel(store.getTel())
                        .mobile(store.getMobile())
                        .businessRegNumber(store.getBusinessRegNumber())
                        .companyId(store.getCompany().getCompanyId())
                        .subCompanyId(store.getSubCompany().getSubCompanyId())
                        .storeId(store.getStoreId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<StoreFrontDTO> getAllStoreFronts() {
        return storeFrontRepo.findAll().stream().map(front ->
                StoreFrontDTO.builder()
                        .storeAddress(front.getStoreAddress())
                        .storeEmail(front.getStoreEmail())
                        .tel(front.getTel())
                        .mobile(front.getMobile())
                        .businessRegNumber(front.getBusinessRegNumber())
                        .companyId(front.getCompany().getCompanyId())
                        .subCompanyId(front.getSubCompany().getSubCompanyId())
                        .storeIds(front.getStore().stream()
                                .map(Store::getStoreId)
                                .collect(Collectors.toList()))
                        .storeFrontId(front.getStorefrontId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<CounterDTO> getAllCounters() {
        return counterRepo.findAll().stream().map(counter ->
                CounterDTO.builder()
                        .counterType(counter.getCounterType())
                        .companyId(counter.getCompany().getCompanyId())
                        .subCompanyId(counter.getSubCompany().getSubCompanyId())
                        .storeFrontId(counter.getStoreFront().getStorefrontId())
                        .counterId(counter.getCounterId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<ScannerDTO> getAllScanners() {
        return scannerRepo.findAll().stream().map(scanner ->
                ScannerDTO.builder()
                        .scannerName(scanner.getScannerName())
                        .scannerSerial(scanner.getScannerSerial())
                        .companyId(scanner.getCompany().getCompanyId())
                        .subcompanyId(scanner.getSubCompany().getSubCompanyId())
                        .counterId(scanner.getCounter().getCounterId())
                        .scannerId(scanner.getScannerId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<POSTerminalDTO> getAllPOSTerminals() {
        return posTerminalRepo.findAll().stream().map(pos ->
                POSTerminalDTO.builder()
                        .posTerminalName(pos.getPosTerminalName())
                        .posTerminalDetails(pos.getPosTerminalDetails())
                        .companyId(pos.getCompany().getCompanyId())
                        .subcompanyId(pos.getSubCompany().getSubCompanyId())
                        .counterId(pos.getCounter().getCounterId())
                        .posId(pos.getPosTerminalId())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<DrawerDTO> getAllDrawers() {
        return cashDrawerRepo.findAll().stream().map(drawer ->
                DrawerDTO.builder()
                        .drawerName(drawer.getDrawerName())
                        .drawerDescription(drawer.getDrawerDescription())
                        .drawerStatus(drawer.getDrawerStatus())
                        .drawerType(drawer.getDrawerType())
                        .companyId(drawer.getCompany().getCompanyId())
                        .subCompanyId(drawer.getSubCompany().getSubCompanyId())
                        .counterId(drawer.getCounter().getCounterId())
                        .drawerId(drawer.getDrawerId())
                        .build()
        ).collect(Collectors.toList());
    }

}
