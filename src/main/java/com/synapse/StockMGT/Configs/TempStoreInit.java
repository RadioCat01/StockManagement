package com.synapse.StockMGT.Configs;

import com.synapse.StockMGT.Models.Store;
import com.synapse.StockMGT.Models.SubCompany;
import com.synapse.StockMGT.Repos.SubComRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TempStoreInit {
    private final SubComRepo subComRepo;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            String storeName1 = "US Computer Technologies";
            String storeName2 = "US Computer Technologies (Pvt) Ltd";

            if(subComRepo.findBySubCompanyName(storeName1).isEmpty()){
                Store store1 = Store.builder()
                        .storeAddress("No.25/5/17, Neralu Place, Kirigampamunuwa\nPolgasowita, Sri Lanka")
                        .storeEmail("info@uscomtech.lk")
                        .tel("0112781073")
                        .mobile("0765451187")
                        .businessRegNumber("F 19436")
                        .build();

                SubCompany subCompany = SubCompany.builder()
                        .subCompanyName(storeName1)
                        .stores(List.of(store1))
                        .build();

                store1.setSubCompany(subCompany);
                subComRepo.save(subCompany);
            }
            if(subComRepo.findBySubCompanyName(storeName2).isEmpty()){
                Store store2 = Store.builder()
                        .storeAddress("No.325, High Level Road, Pitipana"+"\n"+"Homagama, Sri Lanka")
                        .storeEmail("uscomtechinfo@gmail.com")
                        .tel("0112992163")
                        .mobile("0772351187")
                        .businessRegNumber("PV 00303856")
                        .build();
                SubCompany subCompany =SubCompany.builder()
                        .subCompanyName(storeName2)
                        .stores(List.of(store2))
                        .build();
                store2.setSubCompany(subCompany);
                subComRepo.save(subCompany);
            }
        };
    }
}
