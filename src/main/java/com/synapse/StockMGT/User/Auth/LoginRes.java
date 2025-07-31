package com.synapse.StockMGT.User.Auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginRes {
    private String username;
    private List<String> roles;
}
