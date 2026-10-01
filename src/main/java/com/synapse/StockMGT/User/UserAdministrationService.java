package com.synapse.StockMGT.User;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.CompanyRepo;
import com.synapse.StockMGT.Repos.StoreFrontRepo;
import com.synapse.StockMGT.User.Auth.CreateCompanyRequest;
import com.synapse.StockMGT.User.Auth.CreateUserRequest;
import com.synapse.StockMGT.User.Auth.ManagedCompanyResponse;
import com.synapse.StockMGT.User.Auth.ManagedUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdministrationService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final CompanyRepo companyRepo;
    private final StoreFrontRepo storeFrontRepo;
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;

    @Transactional
    public ManagedCompanyResponse createCompany(CreateCompanyRequest request) {
        if (companyRepo.existsByCompanyNameIgnoreCase(request.getCompanyName().trim())) {
            throw new IllegalArgumentException("A company with that name already exists.");
        }
        ensureUsernameAvailable(request.getCompanyAdmin().getUsername());

        Company company = companyRepo.save(Company.builder()
                .companyName(request.getCompanyName().trim())
                .build());
        User companyAdmin = newUser(
                request.getCompanyAdmin().getUsername(),
                request.getCompanyAdmin().getPassword(),
                request.getCompanyAdmin().getAddress(),
                request.getCompanyAdmin().getPhoneNumber(),
                company,
                null,
                Roles.COMPANY_ADMIN);
        userRepo.save(companyAdmin);

        return ManagedCompanyResponse.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ManagedCompanyResponse> getCompanies() {
        User currentUser = User.currentUser();
        List<Company> companies = currentUser.hasRole(Roles.PLATFORM_ADMIN)
                ? companyRepo.findAll()
                : Collections.singletonList(requireCompany(currentUser));
        return companies.stream()
                .map(company -> ManagedCompanyResponse.builder()
                        .companyId(company.getCompanyId())
                        .companyName(company.getCompanyName())
                        .build())
                .toList();
    }

    @Transactional
    public ManagedUserResponse createUser(CreateUserRequest request) {
        User actor = User.currentUser();
        Company company = resolveCompany(actor, request.getCompanyId());
        Roles role = request.getRole();
        if (role == Roles.PLATFORM_ADMIN || role == Roles.ADMIN || role == Roles.USER) {
            throw new AccessDeniedException("This role cannot be assigned through user administration.");
        }
        if (actor.hasRole(Roles.COMPANY_ADMIN) && role != Roles.CASHIER && role != Roles.STOCK_CLERK) {
            throw new AccessDeniedException("Company administrators can create cashiers and stock clerks only.");
        }
        if (role == Roles.COMPANY_ADMIN && !actor.hasRole(Roles.PLATFORM_ADMIN)) {
            throw new AccessDeniedException("Only a platform administrator can create a company administrator.");
        }

        StoreFront storeFront = null;
        if (role == Roles.CASHIER) {
            if (request.getStoreFrontId() == null) {
                throw new IllegalArgumentException("A cashier must be assigned to a store front.");
            }
            storeFront = storeFrontRepo.findById(request.getStoreFrontId())
                    .filter(front -> front.getCompany() != null
                            && front.getCompany().getCompanyId().equals(company.getCompanyId()))
                    .orElseThrow(() -> new AccessDeniedException(
                            "The selected store front does not belong to this company."));
        } else if (request.getStoreFrontId() != null) {
            storeFront = storeFrontRepo.findById(request.getStoreFrontId())
                    .filter(front -> front.getCompany() != null
                            && front.getCompany().getCompanyId().equals(company.getCompanyId()))
                    .orElseThrow(() -> new AccessDeniedException(
                            "The selected store front does not belong to this company."));
        }

        ensureUsernameAvailable(request.getUsername());
        return toResponse(userRepo.save(newUser(
                request.getUsername(),
                request.getPassword(),
                request.getAddress(),
                request.getPhoneNumber(),
                company,
                storeFront,
                role)));
    }

    @Transactional(readOnly = true)
    public List<ManagedUserResponse> getUsers() {
        User actor = User.currentUser();
        List<User> users = actor.hasRole(Roles.PLATFORM_ADMIN)
                ? userRepo.findAll()
                : userRepo.findAllByCompany_CompanyId(requireCompany(actor).getCompanyId());
        return users.stream().map(this::toResponse).toList();
    }

    @Transactional
    public ManagedUserResponse setUserEnabled(Integer userId, boolean enabled) {
        User actor = User.currentUser();
        User target = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (target.hasRole(Roles.PLATFORM_ADMIN) || target.hasRole(Roles.ADMIN)) {
            throw new AccessDeniedException("Platform administrators cannot be managed through this endpoint.");
        }
        Company company = resolveCompany(actor, target.getCompany() == null
                ? null
                : target.getCompany().getCompanyId());
        if (actor.hasRole(Roles.COMPANY_ADMIN)
                && (target.hasRole(Roles.COMPANY_ADMIN) || actor.getId().equals(target.getId()))) {
            throw new AccessDeniedException("Company administrators cannot disable themselves or another administrator.");
        }

        target.setEnabled(enabled);
        if (!enabled) {
            sessionRegistry.getAllPrincipals().stream()
                    .filter(principal -> principal instanceof User
                            && ((User) principal).getId().equals(target.getId()))
                    .forEach(principal -> sessionRegistry.getAllSessions(principal, false)
                            .forEach(session -> session.expireNow()));
        }
        return toResponse(userRepo.save(target));
    }

    private User newUser(
            String username,
            String password,
            String address,
            String phoneNumber,
            Company company,
            StoreFront storeFront,
            Roles roleName) {
        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalStateException("Role not initialized: " + roleName));
        return User.builder()
                .username(username.trim())
                .password(passwordEncoder.encode(password))
                .address(address)
                .phoneNumber(phoneNumber)
                .company(company)
                .storeFront(storeFront)
                .roles(Collections.singletonList(role))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }

    private Company resolveCompany(User actor, Integer requestedCompanyId) {
        if (actor.hasRole(Roles.PLATFORM_ADMIN)) {
            if (requestedCompanyId == null) {
                throw new IllegalArgumentException("A company ID is required when creating a company user.");
            }
            return companyRepo.findById(requestedCompanyId)
                    .orElseThrow(() -> new IllegalArgumentException("Company not found."));
        }
        Company company = requireCompany(actor);
        if (requestedCompanyId != null && !company.getCompanyId().equals(requestedCompanyId)) {
            throw new AccessDeniedException("You cannot manage users for another company.");
        }
        return company;
    }

    private Company requireCompany(User user) {
        if (user.getCompany() == null) {
            throw new AccessDeniedException("The signed-in user is not assigned to a company.");
        }
        return user.getCompany();
    }

    private void ensureUsernameAvailable(String username) {
        if (userRepo.existsByUsernameIgnoreCase(username.trim())) {
            throw new IllegalArgumentException("A user with that username already exists.");
        }
    }

    private ManagedUserResponse toResponse(User user) {
        return ManagedUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .companyId(user.getCompany() == null ? null : user.getCompany().getCompanyId())
                .storeFrontId(user.getStoreFront() == null ? null : user.getStoreFront().getStorefrontId())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream().map(Role::getRoleName).toList())
                .build();
    }
}
