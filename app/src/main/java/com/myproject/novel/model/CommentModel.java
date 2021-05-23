package com.myproject.novel.model;

public class CommentModel {
    public Integer commentId;
    public String content;
    public String createdAt;

    public CommentModel(Integer commentId, String content, String createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
