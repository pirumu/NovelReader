package com.myproject.novel.model;

public class ReplyCommentRequestModel {
    public String content;
    public int novelId;

    public ReplyCommentRequestModel(String content) {
        this.content = content;
        this.novelId = 0;
    }

    public ReplyCommentRequestModel(String content, int novelId) {
        this.content = content;
        this.novelId = novelId;
    }
}
