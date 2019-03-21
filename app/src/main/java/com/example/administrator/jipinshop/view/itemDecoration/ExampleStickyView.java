package com.example.administrator.jipinshop.view.itemDecoration;

import android.view.View;

/**
 * @author 莫小婷
 * @create 2019/3/20
 * @Describe
 */
public class ExampleStickyView implements StickyView {

    @Override
    public boolean isStickyView(View view) {
        return (Boolean) view.getTag();
    }

    @Override
    public int getStickViewType() {
        return 11;
    }
}
