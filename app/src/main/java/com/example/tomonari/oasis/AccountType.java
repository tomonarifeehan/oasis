package com.example.tomonari.oasis;

import java.io.Serializable;

public enum AccountType implements Serializable {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator");

    private final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountType() {
        return accountType;
    }
}
