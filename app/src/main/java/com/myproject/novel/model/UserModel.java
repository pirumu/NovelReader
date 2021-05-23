package com.myproject.novel.model;

public class UserModel {
    public Integer userId;
    public String username;
    public String avatar;

    public UserModel(Integer userId, String username, String avatar) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
    }
}
