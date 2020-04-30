package com.example.administrator.jipinshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.jipinshop.R;

import java.text.DecimalFormat;

/**
 * Created by zh on 2017/8/19.
 */
public class SaleProgressView extends View {

    //商品总数
    private int totalCount;
    //当前卖出数
    private int currentCount;
    //动画需要的
    private int progressCount;
    //售出比例
    private float scale;
    //边框颜色
    private int sideColor;
    //文字颜色
    private int textColor;
    //边框粗细
    private float sideWidth;
    //边框所在的矩形
    private Paint sidePaint;
    //背景矩形
    private RectF bgRectF;
    //进度条开始颜色
    private int startColor;
    //进度条结束颜色
    private int endColor;
    private float radius;
    private int width;
    private int height;
    private PorterDuffXfermode mPorterDuffXfermode;
    private Paint srcPaint;
    private Bitmap fgSrc;
    private Bitmap bgSrc;

    private String nearOverText;
    private String overText;
    private float textSize;

    private Paint textPaint;
    private float nearOverTextWidth;
    private float overTextWidth;
    private float baseLineY;
    private Bitmap bgBitmap;
    private boolean isNeedAnim;


    public void setSideColor(int sideColor) {
        this.sideColor = sideColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public SaleProgressView(Context context) {
        this(context, null);
    }

    public SaleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SaleProgressView);
        sideColor = ta.getColor(R.styleable.SaleProgressView_sideColor,0xffff3c32);
        textColor = ta.getColor(R.styleable.SaleProgressView_textColor,0xffff3c32);
        sideWidth = ta.getDimension(R.styleable.SaleProgressView_sideWidth,dp2px(2));
        overText = ta.getString(R.styleable.SaleProgressView_overText);
        nearOverText = ta.getString(R.styleable.SaleProgressView_nearOverText);
        textSize = ta.getDimension(R.styleable.SaleProgressView_textSize,sp2px(16));
        isNeedAnim = ta.getBoolean(R.styleable.SaleProgressView_isNeedAnim,true);
        startColor = ta.getColor(R.styleable.SaleProgressView_startColor,0x70E25838);
        endColor = ta.getColor(R.styleable.SaleProgressView_endColor,0xFFE25838);
        ta.recycle();
    }

    private void initPaint() {
        sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sidePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        sidePaint.setStrokeWidth(sideWidth);

        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        srcPaint.setStrokeWidth(sideWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        nearOverTextWidth = textPaint.measureText(nearOverText);
        overTextWidth = textPaint.measureText(overText);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的宽高
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        //圆角半径
        radius = height / 2.0f;
        //留出一定的间隙，避免边框被切掉一部分
        if (bgRectF == null) {
            bgRectF = new RectF(sideWidth, sideWidth, width - sideWidth, height - sideWidth);
        }

        if (baseLineY == 0.0f) {
            Paint.FontMetricsInt fm = textPaint.getFontMetricsInt();
            baseLineY = height / 2 - (fm.descent / 2 + fm.ascent / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        sidePaint.setColor(sideColor);

        if(!isNeedAnim){
            progressCount = currentCount;
        }

        if (totalCount == 0) {
            scale = 0.0f;
        } else {
            scale = Float.parseFloat(new DecimalFormat("0.00").format((float) progressCount / (float) totalCount));
        }

        drawSide(canvas);
        drawFg(canvas);

        //这里是为了演示动画方便，实际开发中进度只会增加
        if(progressCount!=currentCount){
            if(progressCount<currentCount){
                progressCount++;
            }else{
//                progressCount--;
                progressCount = 0;
            }
            postInvalidate();
        }

    }

    //绘制背景边框
    private void drawSide(Canvas canvas) {
        canvas.drawRoundRect(bgRectF, radius, radius, sidePaint);
    }

    //绘制进度条
    private void drawFg(Canvas canvas) {
        if (scale == 0.0f) {
            return;
        }
        LinearGradient lg = new LinearGradient(0,0,(width - sideWidth) * scale,height - sideWidth,startColor,endColor, Shader.TileMode.MIRROR);
        srcPaint.setShader(lg);
        canvas.drawRoundRect(new RectF(sideWidth, sideWidth, (width - sideWidth) * scale, height - sideWidth), radius, radius, srcPaint);
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public void setTotalAndCurrentCount(int totalCount, int currentCount) {
        this.totalCount = totalCount;
        if (currentCount > totalCount) {
            currentCount = totalCount;
        }
        this.currentCount = currentCount;
        postInvalidate();
    }

    public  float getSideWidth(){
        float scale = 0.0f;
        if (totalCount == 0) {
            scale = 0.0f;
        } else {
            scale = Float.parseFloat(new DecimalFormat("0.##").format((float) currentCount / (float) totalCount));
        }
        Log.d("SaleProgressView", "width:" + width);
        Log.d("SaleProgressView", "sideWidth:" + sideWidth);
        Log.d("SaleProgressView", "scale:" + scale);
        return (width - sideWidth) * scale;
    }
}
