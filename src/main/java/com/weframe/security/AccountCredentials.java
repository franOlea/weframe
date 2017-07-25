package com.weframe.security;

public class AccountCredentials {

    private String username;
    private String password;

    public AccountCredentials() {
    }

    public AccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
