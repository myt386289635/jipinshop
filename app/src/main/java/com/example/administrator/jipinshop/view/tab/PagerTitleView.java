package com.example.administrator.jipinshop.view.tab;

import android.content.Context;
import android.text.TextPaint;

/**
 * @author 莫小婷
 * @create 2020/3/16
 * @Describe 动态tab加粗
 */
public class PagerTitleView extends SimpleCommenPagerTitleView {

    public PagerTitleView(Context context) {
        super(context,0,0);
    }

    private float mChangePercent = 0.5f;

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
            //取消加粗
            TextPaint paint = getPaint();
            paint.setFakeBoldText(false);
        } else {
            setTextColor(mSelectedColor);
            //加粗
            TextPaint paint = getPaint();
            paint.setFakeBoldText(true);
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
            //加粗
            TextPaint paint = getPaint();
            paint.setFakeBoldText(true);
        } else {
            setTextColor(mNormalColor);
            //取消加粗
            TextPaint paint = getPaint();
            paint.setFakeBoldText(false);
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
