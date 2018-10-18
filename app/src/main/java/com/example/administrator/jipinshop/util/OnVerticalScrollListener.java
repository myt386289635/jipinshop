package com.example.administrator.jipinshop.util;

import android.support.v7.widget.RecyclerView;

/**
 * @author 莫小婷
 * @create 2018/9/1
 * @Describe
 */
public abstract  class OnVerticalScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }
    public void onScrolledUp() {}
    public void onScrolledDown() {}
    public void onScrolledToTop() {}
    public void onScrolledToBottom() {}
}
