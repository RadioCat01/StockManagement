package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.User.Auth.CreateCompanyRequest;
import com.synapse.StockMGT.User.Auth.CreateUserRequest;
import com.synapse.StockMGT.User.Auth.EnabledRequest;
import com.synapse.StockMGT.User.UserAdministrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserAdministrationController {
    private final UserAdministrationService userAdministrationService;

    @PostMapping("/companies")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CreateCompanyRequest request) {
        return ResponseEntity.ok(userAdministrationService.createCompany(request));
    }

    @GetMapping("/companies")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<?> getCompanies() {
        return ResponseEntity.ok(userAdministrationService.getCompanies());
    }

    @PostMapping("/users")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userAdministrationService.createUser(request));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userAdministrationService.getUsers());
    }

    @PatchMapping("/users/{id}/enabled")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<?> setUserEnabled(
            @PathVariable Integer id,
            @Valid @RequestBody EnabledRequest request) {
        return ResponseEntity.ok(
                userAdministrationService.setUserEnabled(id, request.getEnabled()));
    }
}
