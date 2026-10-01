package com.synapse.StockMGT;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Repos.CompanyRepo;
import com.synapse.StockMGT.Repos.StoreFrontRepo;
import com.synapse.StockMGT.User.*;
import com.synapse.StockMGT.User.Auth.CreateCompanyRequest;
import com.synapse.StockMGT.User.Auth.CreateUserRequest;
import com.synapse.StockMGT.User.Auth.LoginAudience;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRoleTests {
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void rolesProduceTheAuthoritiesRequiredBySpringSecurity() {
        User user = User.builder()
                .roles(List.of(
                        Role.builder().roleName(Roles.COMPANY_ADMIN).build(),
                        Role.builder().roleName(Roles.CASHIER).build()))
                .build();

        assertEquals(
                List.of("ROLE_CASHIER", "ROLE_COMPANY_ADMIN"),
                user.getAuthorities().stream().map(Object::toString).sorted().toList());
        assertTrue(user.hasRole(Roles.CASHIER));
        assertFalse(user.hasRole(Roles.PLATFORM_ADMIN));
        assertFalse(User.builder()
                .roles(List.of(Role.builder().roleName(Roles.ADMIN).build()))
                .build().hasRole(Roles.PLATFORM_ADMIN));
    }

    @Test
    void loginAudiencesOnlyAcceptTheirIntendedRoles() {
        User platformAdmin = userWithRole(Roles.PLATFORM_ADMIN);
        User companyAdmin = userWithRole(Roles.COMPANY_ADMIN);
        User stockClerk = userWithRole(Roles.STOCK_CLERK);
        User cashier = userWithRole(Roles.CASHIER);
        User legacyAdmin = userWithRole(Roles.ADMIN);

        assertTrue(LoginAudience.APPLICATION_ADMIN.allows(platformAdmin));
        assertFalse(LoginAudience.APPLICATION_ADMIN.allows(companyAdmin));
        assertTrue(LoginAudience.COMPANY_ADMIN.allows(companyAdmin));
        assertFalse(LoginAudience.COMPANY_ADMIN.allows(platformAdmin));
        assertTrue(LoginAudience.WORKER.allows(stockClerk));
        assertTrue(LoginAudience.WORKER.allows(cashier));
        assertFalse(LoginAudience.WORKER.allows(legacyAdmin));
    }

    @Test
    void platformAdminCreatesCompanyAndHashesItsFirstAdminsPassword() {
        UserRepo users = mock(UserRepo.class);
        RoleRepo roles = mock(RoleRepo.class);
        CompanyRepo companies = mock(CompanyRepo.class);
        StoreFrontRepo storeFronts = mock(StoreFrontRepo.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        org.springframework.security.core.session.SessionRegistry sessions =
                mock(org.springframework.security.core.session.SessionRegistry.class);

        Company company = Company.builder().companyId(7).companyName("Northwind").build();
        Role companyAdminRole = Role.builder().roleName(Roles.COMPANY_ADMIN).build();
        when(companies.existsByCompanyNameIgnoreCase("Northwind")).thenReturn(false);
        when(companies.save(any(Company.class))).thenReturn(company);
        when(users.existsByUsernameIgnoreCase("northwind-admin")).thenReturn(false);
        when(users.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(roles.findByRoleName(Roles.COMPANY_ADMIN)).thenReturn(Optional.of(companyAdminRole));
        when(passwordEncoder.encode("a-long-example-password")).thenReturn("bcrypt-hash");
        authenticateAs(User.builder()
                .roles(List.of(Role.builder().roleName(Roles.PLATFORM_ADMIN).build()))
                .build());

        UserAdministrationService service = new UserAdministrationService(
                users, roles, companies, storeFronts, passwordEncoder, sessions);
        CreateCompanyRequest request = new CreateCompanyRequest();
        request.setCompanyName("Northwind");
        CreateCompanyRequest.CreateCompanyAdmin companyAdmin =
                new CreateCompanyRequest.CreateCompanyAdmin();
        companyAdmin.setUsername("northwind-admin");
        companyAdmin.setPassword("a-long-example-password");
        request.setCompanyAdmin(companyAdmin);

        var response = service.createCompany(request);

        assertEquals(7, response.getCompanyId());
        verify(users).save(argThat(user -> user.getCompany().equals(company)
                && user.hasRole(Roles.COMPANY_ADMIN)
                && user.getPassword().equals("bcrypt-hash")
                && user.isEnabled()));
    }

    @Test
    void companyAdminCannotCreateUsersForAnotherCompany() {
        UserRepo users = mock(UserRepo.class);
        RoleRepo roles = mock(RoleRepo.class);
        CompanyRepo companies = mock(CompanyRepo.class);
        StoreFrontRepo storeFronts = mock(StoreFrontRepo.class);
        authenticateAs(User.builder()
                .id(3)
                .company(Company.builder().companyId(1).build())
                .roles(List.of(Role.builder().roleName(Roles.COMPANY_ADMIN).build()))
                .build());

        UserAdministrationService service = new UserAdministrationService(
                users,
                roles,
                companies,
                storeFronts,
                mock(PasswordEncoder.class),
                mock(org.springframework.security.core.session.SessionRegistry.class));
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("outside-employee");
        request.setPassword("a-long-example-password");
        request.setRole(Roles.CASHIER);
        request.setCompanyId(2);

        assertThrows(AccessDeniedException.class, () -> service.createUser(request));
        verify(users, never()).save(any(User.class));
    }

    private static void authenticateAs(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()));
    }

    private static User userWithRole(Roles role) {
        return User.builder()
                .roles(List.of(Role.builder().roleName(role).build()))
                .build();
    }
}
