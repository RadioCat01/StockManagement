package com.synapse.StockMGT.Configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.synapse.StockMGT.User.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@Slf4j
public class CMD {
    @Value("${app.bootstrap-admin.username:}")
    private String bootstrapAdminUsername;

    @Value("${app.bootstrap-admin.password:}")
    private String bootstrapAdminPassword;

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepo userRepository,
            RoleRepo roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            for (Roles roleName : Roles.values()) {
                roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> roleRepository.save(Role.builder().roleName(roleName).build()));
            }

            if (bootstrapAdminUsername.isBlank() && bootstrapAdminPassword.isBlank()) {
                log.info("Platform administrator bootstrap is disabled; configure APP_BOOTSTRAP_ADMIN_USERNAME and APP_BOOTSTRAP_ADMIN_PASSWORD to create one.");
                return;
            }
            if (bootstrapAdminUsername.isBlank() || bootstrapAdminPassword.isBlank()) {
                throw new IllegalStateException(
                        "Both APP_BOOTSTRAP_ADMIN_USERNAME and APP_BOOTSTRAP_ADMIN_PASSWORD must be configured together.");
            }
            if (bootstrapAdminPassword.length() < 12 || bootstrapAdminPassword.length() > 72) {
                throw new IllegalStateException(
                        "The bootstrap platform administrator password must contain between 12 and 72 characters.");
            }

            User existingUser = userRepository.findByUsername(bootstrapAdminUsername).orElse(null);
            if (existingUser != null) {
                if (!existingUser.hasRole(Roles.PLATFORM_ADMIN) || existingUser.getCompany() != null) {
                    throw new IllegalStateException(
                            "The configured platform administrator username already belongs to a non-platform user.");
                }
                return;
            }

            Role platformAdminRole = roleRepository.findByRoleName(Roles.PLATFORM_ADMIN)
                    .orElseThrow(() -> new IllegalStateException("Platform administrator role was not initialized."));
            userRepository.save(User.builder()
                    .username(bootstrapAdminUsername)
                    .password(passwordEncoder.encode(bootstrapAdminPassword))
                    .roles(Collections.singletonList(platformAdminRole))
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build());
            log.info("Created the configured platform administrator.");
        };
        }
}
