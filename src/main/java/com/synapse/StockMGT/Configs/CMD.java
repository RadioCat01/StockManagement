package com.synapse.StockMGT.Configs;

import com.beust.ah.A;
import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import com.synapse.StockMGT.Repos.CompanyRepo;
import com.synapse.StockMGT.Repos.StoreFrontRepo;
import com.synapse.StockMGT.Repos.StoreRepo;
import com.synapse.StockMGT.Repos.SubComRepo;
import com.synapse.StockMGT.User.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class CMD {
        @Bean
        public CommandLineRunner commandLineRunner(UserRepo userRepository,
                                                   RoleRepo roleRepository,
                                                   CompanyRepo companyRepository,
                                                   SubComRepo subCompanyRepository,
                                                   StoreRepo storeRepository,
                                                   StoreFrontRepo storeFrontRepository) {
            return args -> {
                Role UserRole = roleRepository.findByRoleName(Roles.ADMIN).orElse(null);
                Role AdminRole = roleRepository.findByRoleName(Roles.ADMIN).orElse(null);

                if(UserRole==null && AdminRole!=null){
                    UserRole = roleRepository.save(Role.builder().roleName(Roles.USER).build());
                }
                else if(UserRole!=null && AdminRole==null){
                   AdminRole = roleRepository.save(Role.builder().roleName(Roles.ADMIN).build());
                }
                else if(UserRole==null){
                    UserRole = roleRepository.save(Role.builder().roleName(Roles.USER).build());
                    AdminRole = roleRepository.save(Role.builder().roleName(Roles.ADMIN).build());
                }

                String sampleCompanyName = "TechCorp";
                StoreFront frontA= null;
                if (companyRepository.findByCompanyName(sampleCompanyName).isEmpty()) {
                    frontA = StoreFront.builder()
                            .storeFrontName("Main Front")
                            .build();

                    Store store = Store.builder()
                            .storeName("Central Store")
                            .storeFronts(List.of(frontA))
                            .build();

                    SubCompany subCompany = SubCompany.builder()
                            .subCompanyName("TechCorp Lanka")
                            .stores(List.of(store))
                            .build();

                    Company company = Company.builder()
                            .companyName(sampleCompanyName)
                            .subCompanies(List.of(subCompany))
                            .build();
                    frontA = storeRepository.save(store).getStoreFronts().get(0);
                    subCompany.setCompany(company);
                    store.setStoreFronts(List.of(frontA));

                    companyRepository.save(company);

                } else {
                    System.out.println("Sample company already exists.");
                }
                if(userRepository.findByUsername("ADMIN").isEmpty()){
                    userRepository.save(User.builder()
                            .username("ADMIN")
                            .password(new BCryptPasswordEncoder().encode("password"))
                            .roles(List.of(UserRole,AdminRole))
                            .storeFront(frontA)
                            .enabled(true)
                            .accountNonExpired(true)
                            .accountNonLocked(true)
                            .credentialsNonExpired(true)
                            .build());
                }
            };
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}
