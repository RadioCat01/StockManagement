package com.synapse.StockMGT.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer userId);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String name);

}
