package com.example.tomonari.oasis;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"ClassWithTooManyDependents", "JavaDoc"})
public class User implements Serializable {
    public static final List<AccountType> legalClass = Arrays.asList(AccountType.values());
    private String name;
    private String email;
    private String password;
    private AccountType accountType;
    private String uid;


    /**
     * Empty constructor for Firebase
     *
     */
    public User() {

    }
    /**
     * Normal constructor for a user
     *
     * @param email
     * @param name
     * @param password
     * @param uid
     */
    public User(String email, String password, String name, String uid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountType = accountType;
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}

