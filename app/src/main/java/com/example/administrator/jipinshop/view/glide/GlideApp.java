package com.example.administrator.jipinshop.view.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * @author 莫小婷
 * @create 2018/11/23
 * @Describe Glide工具
 */
public class GlideApp {

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context , String url , ImageView imageView){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(10,0, CenterRoundTransform.CornerType.ALL));
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context , int id , ImageView imageView){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(10,0, CenterRoundTransform.CornerType.ALL));
        Glide.with(context)
                .load(id)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context , String url , ImageView imageView , int error ,int placeholder){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(10,0, CenterRoundTransform.CornerType.ALL));
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context , int url , ImageView imageView , int error ,int placeholder){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(10,0, CenterRoundTransform.CornerType.ALL));
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loderCircleImage(Context context , String url , ImageView imageView, int error ,int placeholder){
        RequestOptions requestOptions = new RequestOptions();
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(bitmapTransform(new CropCircleTransformation()))
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loderCircleImage(Context context , int url , ImageView imageView, int error ,int placeholder){
        RequestOptions requestOptions = new RequestOptions();
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(bitmapTransform(new CropCircleTransformation()))
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载图片
     */
    public static void loderImage(Context context , int url , ImageView imageView, int error ,int placeholder){
        RequestOptions requestOptions = new RequestOptions();
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载图片
     */
    public static void loderImage(Context context , String url , ImageView imageView, int error ,int placeholder){
        RequestOptions requestOptions = new RequestOptions();
        if(error != 0){
            requestOptions =  requestOptions.error(error);
        }
        if(placeholder != 0){
            requestOptions = requestOptions.placeholder(placeholder);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载高斯模糊图片
     */
    public static void loderBlurImage(Context context , String url , ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(bitmapTransform(new BlurTransformation(20)))// “23”：设置模糊度(在0.0到25.0之间)，默认”25";
                .into(imageView);
    }
}
