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
public class More extends RecyclerView.OnScrollListener {
    private boolean isCanLoadMore = true;
    private OnLoadMoreListener listener;

    public static interface OnLoadMoreListener {
        void onLoadMore(RecyclerView recyclerView);
    }

    public More() {
        super();
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        System.out.println(dy);
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (lm == null) return;
        int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
        int itemCount = lm.getItemCount();
        View lastChildView = lm.getChildAt(lm.getChildCount() - 1);
        if (lastChildView == null) return;
        int lastChildBottom = lastChildView.getBottom();
        int recyclerBottom = recyclerView.getBottom();
        int paddingBottom = recyclerView.getPaddingBottom();
        if (lastVisibleItemPosition == itemCount - 1 && lastChildBottom == recyclerBottom - paddingBottom) {
            if (isCanLoadMore) {
                // 业务代码
                if (listener != null) listener.onLoadMore(recyclerView);
                else onLoadMore(recyclerView);
            }
        }
    }

    public void onLoadMore(RecyclerView recyclerView) {

    }

    public void setListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }
}
