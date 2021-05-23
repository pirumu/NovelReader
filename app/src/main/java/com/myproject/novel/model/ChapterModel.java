package com.myproject.novel.model;

public class ChapterModel {
    public Integer chapterId;
    public String chapterTitle;
    public String chapterDescription;

    public ChapterModel(Integer chapterId, String chapterTitle, String chapterDescription) {
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.chapterDescription = chapterDescription;
    }
}
