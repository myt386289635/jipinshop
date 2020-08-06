package com.example.administrator.jipinshop.view.webview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author 莫小婷
 * @create 2020/8/6
 * @Describe
 */
public class MeiTuanWebView extends WebView {

    public MeiTuanWebView(Context context) {
        super(context);
    }

    public MeiTuanWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeiTuanWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOnDrawListener != null) {
            mOnDrawListener.onDrawCallBack();
        }
    }

    private OnDrawListener mOnDrawListener;

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        mOnDrawListener = onDrawListener;
    }

    public interface OnDrawListener {
        void onDrawCallBack();
    }

}
