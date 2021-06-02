package com.myproject.novel.model;

public class TagModel {
    private int tagId;
    private String tagName;
    private String tagDescription;
    private String tagPhotoUrl;
    private int novelsCount;

    public TagModel(int tagId, String tagName, String tagDescription, String tagPhotoUrl, int novelsCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.tagPhotoUrl = tagPhotoUrl;
        this.novelsCount = novelsCount;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public String getTagPhotoUrl() {
        return tagPhotoUrl;
    }

    public void setTagPhotoUrl(String tagPhotoUrl) {
        this.tagPhotoUrl = tagPhotoUrl;
    }

    public int getNovelsCount() {
        return novelsCount;
    }

    public void setNovelsCount(int novelsCount) {
        this.novelsCount = novelsCount;
    }
}
