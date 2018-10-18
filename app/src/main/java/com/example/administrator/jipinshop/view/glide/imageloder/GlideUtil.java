package com.example.administrator.jipinshop.view.glide.imageloder;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.MyApplication;

/**
 * @author 莫小婷
 * @create 2018/8/18
 * @Describe
 */
public class GlideUtil {
    public static void loadImage(String imageUrl, ImageView imageView, int loadingImage) {
        Glide.with(MyApplication.getInstance()).load(imageUrl).placeholder(loadingImage).into(imageView);
    }

    public static void CircleLoadImage(String imageUrl, ImageView imageView) {
        Glide.with(MyApplication.getInstance()).load(imageUrl).centerCrop().into(imageView);
    }

    public static void loadImageGif(String imageUrl, ImageView imageView, int loadingImage) {
        Glide.with(MyApplication.getInstance()).load(imageUrl).asGif().placeholder(loadingImage).into(imageView);
    }
}
