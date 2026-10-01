package com.synapse.StockMGT.User.Auth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ManagedCompanyResponse {
    Integer companyId;
    String companyName;
}
