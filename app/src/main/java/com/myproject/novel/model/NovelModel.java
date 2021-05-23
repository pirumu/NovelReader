package com.myproject.novel.model;

public class NovelModel {
    public int novelId;
    public String novelTitle;
    public String novelDescription;
    public String novelUrl;

    public NovelModel(int novelId, String novelTitle, String novelDescription, String novelUrl) {
        this.novelId = novelId;
        this.novelTitle = novelTitle;
        this.novelDescription = novelDescription;
        this.novelUrl = novelUrl;
    }
}
