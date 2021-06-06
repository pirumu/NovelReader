package com.myproject.novel.model;

public class RegisterRequestModel {
    public String email;
    public String nickname;
    public String password;

    public RegisterRequestModel(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
