package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.administrator.jipinshop.R;

public class CustomRefreshHeadView extends RelativeLayout implements SwipeRefreshTrigger, SwipeTrigger {

    private TextView refresh_text;
    private ImageView refresh_image;
    private AnimationDrawable mAnimDrawable;

    public CustomRefreshHeadView(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = View.inflate(getContext(), R.layout.header, null);
        refresh_text = view.findViewById(R.id.refresh_text);
        refresh_image = view.findViewById(R.id.refresh_image);
        addView(view, lp);
        setPadding(0,0,0,0);
        mAnimDrawable = (AnimationDrawable) refresh_image.getBackground();
    }

    @Override
    public void onRefresh() {
        refresh_text.setText("正在刷新");
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (y >= getHeight()) {
                refresh_text.setText("松开刷新");
            } else {
                refresh_text.setText("下拉刷新");

            }
        } else {
            refresh_text.setText("下拉刷新");
        }
        float scale;
        if (y <= getHeight()){
            scale = (float) (y / Double.valueOf(getHeight()));
        }else {
            scale = 1;
        }
        refresh_image.setScaleX(scale);
        refresh_image.setScaleY(scale);
    }

    @Override
    public void onRelease() {
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }
    }

    @Override
    public void onComplete() {
        refresh_text.setText("刷新成功");
    }

    @Override
    public void onReset() {
        refresh_text.setText("");
        mAnimDrawable.stop();
    }
}
