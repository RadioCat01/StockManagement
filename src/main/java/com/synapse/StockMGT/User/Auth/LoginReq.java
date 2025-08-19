package com.synapse.StockMGT.User.Auth;

import lombok.Data;

@Data
public class LoginReq {
    private String username;
    private String password;
}
