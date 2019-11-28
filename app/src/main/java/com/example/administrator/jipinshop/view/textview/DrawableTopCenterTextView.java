package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * @author 莫小婷
 * @create 2019/11/7
 * @Describe
 */
public class DrawableTopCenterTextView extends AppCompatTextView implements Checkable {

    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public DrawableTopCenterTextView(Context context) {
        super(context);
    }

    public DrawableTopCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTopCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = getCompoundDrawables();
        // 得到drawabletop设置的drawable对象
        Drawable topDrawable = drawables[1];
        if (topDrawable != null) {
            // 得到topDrawable的宽度
            int topDrawableHeight = topDrawable.getIntrinsicHeight();
            // 得到drawable与text之间的间距
            int drawablePadding = getCompoundDrawablePadding();
            // 得到文本的高度
            int textHeight = (int) getFontHeight(getPaint());
            int bodyHeight = topDrawableHeight + drawablePadding + textHeight;
            canvas.save();
            canvas.translate(0, (getHeight() - bodyHeight) / 2);
        }
        super.onDraw(canvas);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }


    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }


    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        mChecked = !mChecked;
    }
}
