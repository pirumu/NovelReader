package com.myproject.novel.model;

public class ResetPasswordResponseModel {
    public int code;
    public String msg;


    public ResetPasswordResponseModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
