package com.myproject.novel.ui.filter;

public interface LoadMoreListener {

    boolean hasMoreToLoad();

    void fetchNextPage();
}