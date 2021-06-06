package com.myproject.novel.ui.filter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class LoadMoreRecyclerViewScrollListener extends EndlessRecyclerViewScrollListener implements LoadMoreListener {

    private boolean hasMoreToLoad = true;

    public LoadMoreRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        super(layoutManager);
    }

    public LoadMoreRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        super(layoutManager);
    }

    public LoadMoreRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        if (hasMoreToLoad()) {
            fetchNextPage();
        }
    }

    @Override
    public boolean hasMoreToLoad() {
        return hasMoreToLoad;
    }

    public void setHasMoreToLoad(boolean hasMore) {
        hasMoreToLoad = hasMore;
    }
}