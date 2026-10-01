package com.synapse.StockMGT.User.Auth;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateCompanyRequest {
    @NotBlank
    @Size(max = 150)
    private String companyName;

    @Valid
    @NotNull
    private CreateCompanyAdmin companyAdmin;

    @Data
    public static class CreateCompanyAdmin {
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
    }
}
