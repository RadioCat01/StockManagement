package com.synapse.StockMGT.User;

import com.synapse.StockMGT.Models.CompanyHierarchy.Company;
import com.synapse.StockMGT.Models.CompanyHierarchy.Store;
import com.synapse.StockMGT.Models.CompanyHierarchy.SubCompany;
import com.synapse.StockMGT.Models.CompanyHierarchy.StoreFront;
import com.synapse.StockMGT.Repos.CompanyRepo;
import com.synapse.StockMGT.Repos.StoreFrontRepo;
import com.synapse.StockMGT.Repos.StoreRepo;
import com.synapse.StockMGT.Repos.SubComRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessScopeService {
    private final CompanyRepo companyRepo;
    private final SubComRepo subComRepo;
    private final StoreRepo storeRepo;
    private final StoreFrontRepo storeFrontRepo;

    public User currentUser() {
        return User.currentUser();
    }

    public Integer companyId() {
        User user = currentUser();
        if (user.hasRole(Roles.PLATFORM_ADMIN)) {
            return null;
        }
        if (user.getCompany() == null) {
            throw new AccessDeniedException("Your account is not assigned to a company.");
        }
        return user.getCompany().getCompanyId();
    }

    public boolean isPlatformAdmin(User user) {
        return user.hasRole(Roles.PLATFORM_ADMIN);
    }

    public boolean isCompanyVisible(Integer companyId) {
        User user = currentUser();
        return user.hasRole(Roles.PLATFORM_ADMIN)
                || user.getCompany() != null
                && companyId != null
                && companyId.equals(user.getCompany().getCompanyId());
    }

    public Company requireCompany(Integer requestedCompanyId) {
        User user = currentUser();
        Integer companyId = requestedCompanyId;
        if (!user.hasRole(Roles.PLATFORM_ADMIN)) {
            if (user.getCompany() == null) {
                throw new AccessDeniedException("Your account is not assigned to a company.");
            }
            companyId = user.getCompany().getCompanyId();
            if (requestedCompanyId != null && !requestedCompanyId.equals(companyId)) {
                throw new AccessDeniedException("You cannot access another company.");
            }
        }
        if (companyId == null) {
            throw new IllegalArgumentException("A company must be selected.");
        }
        return companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found."));
    }

    public SubCompany requireSubCompany(Integer subCompanyId, Integer expectedCompanyId) {
        SubCompany subCompany = subComRepo.findById(subCompanyId)
                .orElseThrow(() -> new IllegalArgumentException("Sub-company not found."));
        if (subCompany.getCompany() == null
                || !subCompany.getCompany().getCompanyId().equals(expectedCompanyId)) {
            throw new AccessDeniedException("The selected sub-company does not belong to this company.");
        }
        return subCompany;
    }

    public StoreFront requireStoreFront(Integer storeFrontId, Integer expectedCompanyId) {
        StoreFront storeFront = storeFrontRepo.findById(storeFrontId)
                .orElseThrow(() -> new IllegalArgumentException("Store front not found."));
        if (storeFront.getCompany() == null
                || !storeFront.getCompany().getCompanyId().equals(expectedCompanyId)) {
            throw new AccessDeniedException("The selected store front does not belong to this company.");
        }
        return storeFront;
    }

    public Store requireStore(Integer storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found."));
        User user = currentUser();
        if (user.hasRole(Roles.PLATFORM_ADMIN)) {
            return store;
        }
        if (user.getCompany() == null || store.getCompany() == null
                || !user.getCompany().getCompanyId().equals(store.getCompany().getCompanyId())) {
            throw new AccessDeniedException("The selected store does not belong to your company.");
        }
        if (user.hasRole(Roles.CASHIER)
                && (user.getStoreFront() == null || store.getStoreFronts() == null
                || store.getStoreFronts().stream().noneMatch(front ->
                front.getStorefrontId().equals(user.getStoreFront().getStorefrontId())))) {
            throw new AccessDeniedException("The selected store is not assigned to your store front.");
        }
        return store;
    }
}
