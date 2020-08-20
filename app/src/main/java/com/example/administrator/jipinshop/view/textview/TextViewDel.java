package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.DistanceHelper;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe 自定义TextView删除线
 */
public class TextViewDel extends AppCompatTextView {
    private boolean flag;
    private int mColor = R.color.color_E31436;
    private Paint paint;

    public TextViewDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (flag) {
            // 设置直线的颜色
            paint.setColor(getResources().getColor(mColor));
            // 设置直线没有锯齿
            paint.setAntiAlias(true);
            // 设置线宽
            paint.setStrokeWidth(DistanceHelper.dip2px(1));
            // 设置直线位置
            canvas.drawLine(0, this.getHeight() / 2 + DistanceHelper.dip2px(1), this.getWidth(), this.getHeight() / 2 + DistanceHelper.dip2px(1), paint);
        }

    }

    /**
     * true显示删除线     false不显示删除线
     *
     * @param flag
     * @return flag
     */

    public boolean setTv(boolean flag) {
        this.flag = flag;
        return flag;
    }

    public void setColor(int color){
        mColor = color;
    }
}
