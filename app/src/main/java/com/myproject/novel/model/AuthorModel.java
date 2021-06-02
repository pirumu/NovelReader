package com.myproject.novel.model;

public class AuthorModel {

    private int authorId;
    private String authorName;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public AuthorModel(int authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }
}
