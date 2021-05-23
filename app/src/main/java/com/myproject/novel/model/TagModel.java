package com.myproject.novel.model;

public class TagModel {
    public int Id;
    public String title;
    public String desc;
    public String url;

    public TagModel(int id, String title, String desc, String url) {
        Id = id;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }
}
