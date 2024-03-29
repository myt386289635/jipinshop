package com.example.administrator.jipinshop.view.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.jipinshop.R;

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
    public static void loderRoundImage(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform((int) context.getResources().getDimension(R.dimen.x10), 0, CenterRoundTransform.CornerType.ALL));
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context, int id, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform((int) context.getResources().getDimension(R.dimen.x10), 0, CenterRoundTransform.CornerType.ALL));
        if (context != null) {
            Glide.with(context)
                    .load(id)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context, String url, ImageView imageView, int error, int placeholder) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform((int) context.getResources().getDimension(R.dimen.x10), 0, CenterRoundTransform.CornerType.ALL));
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     */
    public static void loderRoundImage(Context context, int url, ImageView imageView, int error, int placeholder) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform((int) context.getResources().getDimension(R.dimen.x10), 0, CenterRoundTransform.CornerType.ALL));
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     */
    public static void loderCircleImage(Context context, String url, ImageView imageView, int error, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     */
    public static void loderCircleImage(Context context, int url, ImageView imageView, int error, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载图片
     */
    public static void loderImage(Context context, int url, ImageView imageView, int error, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载图片
     */
    public static void loderImage(Context context, String url, ImageView imageView, int error, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }
        if (placeholder != 0) {
            requestOptions = requestOptions.placeholder(placeholder);
        }
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载高斯模糊图片
     */
    public static void loderBlurImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(bitmapTransform(new BlurTransformation(20)))// “23”：设置模糊度(在0.0到25.0之间)，默认”25";
                    .into(imageView);
        }
    }

    /**
     * 上圆角，下直角
     */
    public static void loderTopRoundImage(Context context, String url, ImageView imageView,int radius) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(radius, 0, CenterRoundTransform.CornerType.TOP));
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 左圆角，右直角
     */
    public static void loderLeftRoundImage(Context context, String url, ImageView imageView,int radius) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(radius, 0, CenterRoundTransform.CornerType.LEFT));
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 右圆角，左直角
     */
    public static void loderRightRoundImage(Context context, String url, ImageView imageView,int radius) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .transform(new CenterRoundTransform(radius, 0, CenterRoundTransform.CornerType.RIGHT));
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    //判断Activity是否Destroy
    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}
