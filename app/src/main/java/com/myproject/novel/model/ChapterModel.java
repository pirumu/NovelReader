package com.myproject.novel.model;

public class ChapterModel {
    private Integer chapterId;
    private String chapterTitle;
    private String createdAt;
    private Integer novelId;

    public ChapterModel(Integer chapterId, Integer novelId, String chapterTitle, String createdAt) {
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.createdAt = createdAt;
        this.novelId = novelId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
    }
}
