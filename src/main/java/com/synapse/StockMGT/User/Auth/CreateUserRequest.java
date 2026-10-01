package com.synapse.StockMGT.User.Auth;

import com.synapse.StockMGT.User.Roles;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {
    @NotBlank
    @Size(max = 80)
    private String username;

    @NotBlank
    @Size(min = 12, max = 72)
    private String password;

    @Size(max = 250)
    private String address;

    @Size(max = 40)
    private String phoneNumber;

    @NotNull
    private Roles role;

    private Integer companyId;
    private Integer storeFrontId;
}
