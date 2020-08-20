package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.ToastUtil;

/**
 * @author 莫小婷
 * @create 2019/10/25
 * @Describe
 */
public class CollapsibleTextView extends LinearLayout implements View.OnClickListener {

    /** default text show max lines */
    private static final int DEFAULT_MAX_LINE_COUNT = 3;

    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private TextView desc;
    private ImageView descOp;
    private LinearLayout desc_container;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;
    private Boolean originOnce;
    private int totleLine;
    private Runnable mRunnable;

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.collapsible_textview, this);
        shrinkup="收起";
        spread="查看更多";
        view.setPadding(0, -1, 0, 0);
        desc = view.findViewById(R.id.desc_tv);
        descOp = view.findViewById(R.id.desc_op_tv);
        desc_container = view.findViewById(R.id.desc_container);
        desc_container.setOnClickListener(this);
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
                descOp.setImageResource(R.mipmap.right_down);
                mState = COLLAPSIBLE_STATE_SHRINKUP;
            } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                desc.setMaxLines(Integer.MAX_VALUE);
                descOp.setVisibility(View.VISIBLE);
                descOp.setImageResource(R.mipmap.right_up);
                mState = COLLAPSIBLE_STATE_SPREAD;
            }
        }
    }
}
