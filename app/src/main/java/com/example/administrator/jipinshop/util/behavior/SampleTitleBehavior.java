package com.example.administrator.jipinshop.util.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2019/3/22
 * @Describe
 */
public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;
    private int hight  = 0;

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        hight = (int) context.getResources().getDimension(R.dimen.y92);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    /**
     * @param parent CoordinatorLayout
     * @param child  这个就是app:layout_behavior="@string/behavior_sample_title" 的view,即：自己，依赖的view
     * @param dependency 被依赖的view, 自己依赖的对象
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = (dependency.getY() - hight) - child.getHeight();
        }

        float dy = (dependency.getY() - hight) - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        y = y < -child.getHeight() ? -child.getHeight() : y;
        child.setTranslationY(y);

        return true;
    }
}
