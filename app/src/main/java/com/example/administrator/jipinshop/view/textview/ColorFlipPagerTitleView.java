package com.example.administrator.jipinshop.view.textview;

import android.content.Context;

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
public class ColorFlipPagerTitleView extends SimpleCommenPagerTitleView {

    private float mChangePercent = 0.5f;

    public ColorFlipPagerTitleView(Context context,int leftPadding , int rightPadding) {
        super(context,leftPadding,rightPadding);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
        } else {
            setTextColor(mSelectedColor);
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
        } else {
            setTextColor(mNormalColor);
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    public float getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(float changePercent) {
        mChangePercent = changePercent;
    }
}
