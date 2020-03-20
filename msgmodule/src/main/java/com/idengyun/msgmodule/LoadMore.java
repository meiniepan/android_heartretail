package com.idengyun.msgmodule;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 商品评价加载更多助手类
 *
 * @author aLang
 */
public final class LoadMore {

    public boolean isRequestLoadMore() {
        return requestLoadMore;
    }

    private boolean requestLoadMore;

    public LoadMore(@NonNull RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private int scrollState;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            scrollState = newState;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            requestLoadMore = false;
            if (scrollState != RecyclerView.SCROLL_STATE_IDLE && dy > 0) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (lm == null) return;
                int itemCount = lm.getItemCount();
                View lastChildView = lm.getChildAt(lm.getChildCount() - 1);
                if (lastChildView == null) return;
                int lastCompletelyVisibleItemPosition = lm.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == itemCount - 1) {
                    requestLoadMore = true;
                    System.out.println("--------");
                }
            }
        }
    };

    /*@Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        scrollState = newState;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        loadMore = false;
        if (scrollState != RecyclerView.SCROLL_STATE_IDLE && dy > 0) {
            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (lm == null) return;
            int itemCount = lm.getItemCount();
            View lastChildView = lm.getChildAt(lm.getChildCount() - 1);
            if (lastChildView == null) return;
            int lastCompletelyVisibleItemPosition = lm.findLastCompletelyVisibleItemPosition();
            if (lastCompletelyVisibleItemPosition == itemCount - 1) {
                loadMore = true;
            }
        }
    }*/
}
