package com.example.administrator.jipinshop.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.GlideApp;

/**
 * @author 莫小婷
 * @create 2019/3/15
 * @Describe BindingAdapter工具类
 */
public class BindingUtil {

    @BindingAdapter("bind:text")
    public static void setText(TextView view, int status){
        if(status == 1){
            view.setText("待发货");
        }else if(status == 2){
            view.setText("待收货");
        }else {
            view.setText("已完成");
        }
    }

    @BindingAdapter("bind:srcRound")
    public static void setImage(ImageView imageView, String src){
        GlideApp.loderRoundImage(imageView.getContext(),src,imageView, R.color.transparent,R.color.transparent);
    }

}
