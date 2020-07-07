package com.example.administrator.jipinshop.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author 莫小婷
 * @create 2020/7/6
 * @Describe 横向跑马灯  解决textView获取焦点的问题
 */
public class AlwaysMarqueeTextView extends TextView {

    public AlwaysMarqueeTextView(Context context){
        super(context);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    public boolean isFocused(){
        return true;
    }
}
