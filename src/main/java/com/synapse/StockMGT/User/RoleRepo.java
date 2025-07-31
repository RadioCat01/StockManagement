package com.synapse.StockMGT.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(Roles roleName);
}
