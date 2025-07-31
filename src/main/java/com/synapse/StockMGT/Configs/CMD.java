package com.synapse.StockMGT.Configs;

import com.beust.ah.A;
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
        public CommandLineRunner commandLineRunner(UserRepo userRepository, RoleRepo roleRepository) {
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

                if(userRepository.findByUsername("ADMIN").isEmpty()){
                    userRepository.save(User.builder()
                            .username("ADMIN")
                            .password(new BCryptPasswordEncoder().encode("password"))
                            .roles(List.of(UserRole,AdminRole))
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
