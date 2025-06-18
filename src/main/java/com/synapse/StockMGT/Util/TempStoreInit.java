//package com.synapse.StockMGT.Util;
//
//import com.synapse.StockMGT.Models.CompanyHierarchy.*;
//import com.synapse.StockMGT.Repos.SubComRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class TempStoreInit {
//    private final SubComRepo subComRepo;
//
//    @Bean
//    public CommandLineRunner init() {
//        return args -> {
//            String storeName1 = "US Computer Technologies";
//            String storeName2 = "US Computer Technologies (Pvt) Ltd";
//
//            if(subComRepo.findBySubCompanyName(storeName1).isEmpty()){
//                Counter counter1 = new Counter();
//
//                PosTerminal posTerminal1 = PosTerminal.builder()
//                        .counter(counter1)
//                        .build();
//
//                Scanner scanner = Scanner.builder()
//                        .scannerName("scanner1")
//                        .scannerSerial("0001")
//                        .counter(counter1)
//                        .build();
//
//                counter1.setScanners(List.of(scanner));
//                counter1.setPosTerminals(List.of(posTerminal1));
//
//                StoreFront storeFront = StoreFront.builder()
//                        .storeAddress("No.25/5/17, Neralu Place, Kirigampamunuwa\nPolgasowita, Sri Lanka")
//                        .storeEmail("info@uscomtech.lk")
//                        .tel("0112781073")
//                        .mobile("0765451187")
//                        .businessRegNumber("F 19436")
//                        .counter(List.of(counter1))
//                        .build();
//
//                counter1.setStoreFront(storeFront);
//
//                Store store1 = Store.builder()
//                        .storeAddress("No.25/5/17, Neralu Place, Kirigampamunuwa\nPolgasowita, Sri Lanka")
//                        .storeEmail("info@uscomtech.lk")
//                        .tel("0112781073")
//                        .mobile("0765451187")
//                        .businessRegNumber("F 19436")
//                        .build();
//
//                storeFront.setStore(List.of(store1));
//                store1.setStoreFronts(List.of(storeFront));
//
//                SubCompany subCompany = SubCompany.builder()
//                        .subCompanyName(storeName1)
//                        .stores(List.of(store1))
//                        .build();
//
//                store1.setSubCompany(subCompany);
//                subComRepo.save(subCompany);
//            }
//            if(subComRepo.findBySubCompanyName(storeName2).isEmpty()){
//                Counter counter2 = new Counter();
//
//                PosTerminal posTerminal2 = PosTerminal.builder()
//                        .counter(counter2)
//                        .build();
//
//                Scanner scanner2 = Scanner.builder()
//                        .scannerName("scanner2")
//                        .scannerSerial("0002")
//                        .counter(counter2)
//                        .build();
//
//                counter2.setScanners(List.of(scanner2));
//                counter2.setPosTerminals(List.of(posTerminal2));
//
//                StoreFront storeFront = StoreFront.builder()
//                        .storeAddress("No.325, High Level Road, Pitipana"+"\n"+"Homagama, Sri Lanka")
//                        .storeEmail("uscomtechinfo@gmail.com")
//                        .tel("0112992163")
//                        .mobile("0772351187")
//                        .businessRegNumber("PV 00303856")
//                        .counter(List.of(counter2))
//                        .build();
//
//                counter2.setStoreFront(storeFront);
//
//                Store store2 = Store.builder()
//                        .storeAddress("No.325, High Level Road, Pitipana"+"\n"+"Homagama, Sri Lanka")
//                        .storeEmail("uscomtechinfo@gmail.com")
//                        .tel("0112992163")
//                        .mobile("0772351187")
//                        .businessRegNumber("PV 00303856")
//                        .build();
//
//                storeFront.setStore(List.of(store2));
//                store2.setStoreFronts(List.of(storeFront));
//
//                SubCompany subCompany =SubCompany.builder()
//                        .subCompanyName(storeName2)
//                        .stores(List.of(store2))
//                        .build();
//                store2.setSubCompany(subCompany);
//                subComRepo.save(subCompany);
//            }
//        };
//    }
//}
