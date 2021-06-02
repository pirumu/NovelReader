package com.myproject.novel.model;

public class CommentModel {
    private int commentId;
    private String content;
    private String createdAt;
    private String nickName;
    private String avatar;
    private int parentId;
    private int totalLike;
    private int userId;

    public CommentModel(int commentId, String content, String createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public CommentModel(int commentId, String content, String createdAt, String nickName, String avatar, int parentId, int totalLike, int userId) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.nickName = nickName;
        this.avatar = avatar;
        this.parentId = parentId;
        this.totalLike = totalLike;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
