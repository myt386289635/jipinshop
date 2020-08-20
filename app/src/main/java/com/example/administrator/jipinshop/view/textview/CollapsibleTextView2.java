package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2019/11/26
 * @Describe 商品详情中 推荐理由
 */
public class CollapsibleTextView2 extends LinearLayout implements View.OnClickListener{
    /** default text show max lines */
    private static final int DEFAULT_MAX_LINE_COUNT = 10;

    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private TextView desc;
    private TextView descOp;
    private ImageView desc_img;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;
    private Boolean originOnce = true;
    private int totleLine;
    private Runnable mRunnable;

    public CollapsibleTextView2(Context context) {
        this(context, null);
    }

    public CollapsibleTextView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.collapsible_textview2, this);
        shrinkup="点击收起";
        spread="展开更多";
        view.setPadding(0, -1, 0, 0);
        desc = view.findViewById(R.id.desc_tv);
        descOp = view.findViewById(R.id.desc_op_tv);
        desc_img = view.findViewById(R.id.desc_img);
        descOp.setOnClickListener(this);
    }

    /**
     1.
     2. 对外提供暴漏的方法，为文本提供数据
     3. @param charSequence  文本内容是什么
     4. @param bufferType
     */
    public final void setDesc(String charSequence) {
        desc.setText(charSequence);
        init();
        requestLayout();
    }

    /**
     * 初始化
     */
    public final void init() {
        desc.setMaxLines(Integer.MAX_VALUE);
        mState = COLLAPSIBLE_STATE_SPREAD;
        originOnce = true;
        totleLine = 0;
        flag = false;
        mRunnable = new InnerRunnable();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (originOnce){
            totleLine = desc.getLineCount();
            originOnce = false;
        }
        if (!flag) {
            flag = true;
            if (totleLine <= DEFAULT_MAX_LINE_COUNT) {
                mState = COLLAPSIBLE_STATE_NONE;
                descOp.setVisibility(View.GONE);
                desc_img.setVisibility(VISIBLE);
                desc.setMaxLines(Integer.MAX_VALUE);
            } else {
                post(mRunnable);
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == COLLAPSIBLE_STATE_SPREAD) {
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                descOp.setVisibility(View.VISIBLE);
                Drawable drawable= getResources().getDrawable(R.mipmap.right_down);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_CACACA));
                descOp.setCompoundDrawables(null,null,drawable,null);
                descOp.setText(spread);
                desc_img.setVisibility(GONE);
                mState = COLLAPSIBLE_STATE_SHRINKUP;
            } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                desc.setMaxLines(Integer.MAX_VALUE);
                descOp.setVisibility(View.VISIBLE);
                Drawable drawable= getResources().getDrawable(R.mipmap.right_up);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    drawable.setTint(getResources().getColor(R.color.color_CACACA));
//                }else {
//                    drawable = DrawableCompat.wrap(drawable);
//                    DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_CACACA));
//                }
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_CACACA));
                descOp.setCompoundDrawables(null,null,drawable,null);
                descOp.setText(shrinkup);
                desc_img.setVisibility(VISIBLE);
                mState = COLLAPSIBLE_STATE_SPREAD;
            }
        }
    }
}
