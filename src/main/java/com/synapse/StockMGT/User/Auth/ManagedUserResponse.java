package com.synapse.StockMGT.User.Auth;

import com.synapse.StockMGT.User.Roles;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ManagedUserResponse {
    Integer id;
    String username;
    String address;
    String phoneNumber;
    Integer companyId;
    Integer storeFrontId;
    boolean enabled;
    List<Roles> roles;
}
