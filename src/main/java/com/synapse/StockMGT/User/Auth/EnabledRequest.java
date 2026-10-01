package com.synapse.StockMGT.User.Auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnabledRequest {
    @NotNull
    private Boolean enabled;
}
