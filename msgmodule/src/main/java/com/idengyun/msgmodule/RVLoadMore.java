package com.idengyun.msgmodule;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 加载更多助手类
 *
 * @author aLang
 */
public abstract class RVLoadMore extends RecyclerView.OnScrollListener {

    /* 是否可以加载更多 */
    private boolean isCanLoadMore;
    /* 是否正在加载更多中 */
    private boolean isLoadingMore;

    @Override
    public final void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            loadMore(recyclerView);
        }
    }

    @Override
    public final void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            loadMore(recyclerView);
        }
    }

    private void loadMore(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) lm;
            int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
            int itemCount = llm.getItemCount();
            if (lastVisibleItemPosition == itemCount - 1) {
                synchronized (this) {
                    if (isCanLoadMore && !isLoadingMore) {
                        isLoadingMore = true;
                        onLoadingMore(this);
                    }
                }
            }
        }
    }

    public abstract void onLoadingMore(RVLoadMore listener);

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.isCanLoadMore = canLoadMore;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public boolean isCanLoadMore() {
        return isCanLoadMore;
    }
}
