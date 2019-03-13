package com.example.administrator.jipinshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe
 */
public class MyExtendableListView extends ExpandableListView {

    public MyExtendableListView(Context context) {
        super(context);
    }

    public MyExtendableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExtendableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写的onMeasure方法
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
