package com.myproject.novel.model;

public class SwipeModel {
    public int Id;
    public String title;
    public String desc;
    public String url;

    public SwipeModel(int id, String title, String desc, String url) {
        Id = id;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }
}
