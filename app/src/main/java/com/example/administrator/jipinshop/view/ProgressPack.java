package com.example.administrator.jipinshop.view;

import android.widget.ProgressBar;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe 动态修改宽度
 */
public class ProgressPack {
    ProgressBar mProgressBar;

    //构造方法传入要修改的textView
    public ProgressPack(ProgressBar textView){
        this.mProgressBar = textView;
    }
    public void setWidth(int width){
        //修改宽度
        mProgressBar.getLayoutParams().width = width;
        //重绘
        mProgressBar.setLayoutParams(mProgressBar.getLayoutParams());
    }
    public int getWidth(){
        //获取宽度,此处如果用extView.getLayoutParams().width获取的话，
        // 当layout_width属性的值为wrap_content、match_parent时获取到的是定义的常量值不是精确的宽度
        return mProgressBar.getWidth();
    }
}
