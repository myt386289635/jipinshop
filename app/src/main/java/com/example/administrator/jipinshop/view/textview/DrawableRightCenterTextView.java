package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author 莫小婷
 * @create 2019/2/27
 * @Describe 自定义控件，使用drawableRight与text水平居中显示
 */
public class DrawableRightCenterTextView extends AppCompatTextView {
    public DrawableRightCenterTextView(Context context) {
        super(context);
    }

    public DrawableRightCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableRightCenterTextView(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();//left,top,right,bottom
        if(drawables != null){
            Drawable drawableRight = drawables[2];
            if(drawableRight != null){
                int textWidth = (int) getPaint().measureText(getText().toString().trim());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableRight.getIntrinsicWidth();
                int bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
