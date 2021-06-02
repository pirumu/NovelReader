package com.myproject.novel.model;

public class LoginRequestModel {

    public String email;
    public String password;

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
