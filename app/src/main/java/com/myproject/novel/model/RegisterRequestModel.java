package com.myproject.novel.model;

public class RegisterRequestModel {
    public String email;
    public String fullName;
    public String password;

    public RegisterRequestModel(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }
}
