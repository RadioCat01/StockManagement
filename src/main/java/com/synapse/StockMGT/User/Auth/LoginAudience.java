package com.synapse.StockMGT.User.Auth;

import com.synapse.StockMGT.User.Roles;
import com.synapse.StockMGT.User.User;

public enum LoginAudience {
    APPLICATION_ADMIN {
        @Override
        public boolean allows(User user) {
            return user.hasRole(Roles.PLATFORM_ADMIN);
        }
    },
    COMPANY_ADMIN {
        @Override
        public boolean allows(User user) {
            return user.hasRole(Roles.COMPANY_ADMIN);
        }
    },
    WORKER {
        @Override
        public boolean allows(User user) {
            return user.hasRole(Roles.STOCK_CLERK) || user.hasRole(Roles.CASHIER);
        }
    };

    public abstract boolean allows(User user);
}
