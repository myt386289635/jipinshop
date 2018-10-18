package com.example.administrator.jipinshop.view.glide.imageloder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.view.glide.CircleImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.concurrent.Executor;

/**
 * @author 莫小婷
 * @create 2018/8/18
 * @Describe ImageLoder图片加载器，目前没使用Glide加载是因为Glide加载圆角图片会造成大量的内存溺出。
 *      项目中圆角图片太多，目前没有找到具体的优化方案。
 */
public class ImageManager {
    private static final String TAG = ImageManager.class.getName();
    private static ImageLoader mImageLoader;


    public static void displayImage(String uri, final ImageView view, final int loadingResId, final int errorResId) {
        //   displayImage(uri, view, loadingResId, errorResId, null, false,false,0);
        if(view instanceof CircleImageView){
            displayImage(uri, view, loadingResId, errorResId, false,false,0);
        }
//        else if(!TextUtils.isEmpty(uri)&&uri.startsWith("http")){
//            GlideUtil.loadImage(uri,view, loadingResId);
//        }
        else{
            displayImage(uri, view, loadingResId, errorResId, false,false,0);
        }

    }


    /**
     * @param loadAnimation 加载时渐变动画
     */
    public static void displayImage(String uri, final ImageView view, final int loadingResId, final int errorResId, boolean loadAnimation) {
        displayImage(uri, view, loadingResId, errorResId, true,false,0);
    }

    /**
     * 加载圆型图片
     * @param uri
     * @param view
     * @param loadingResId
     * @param errorResId
     */
    public  static  void displayCircleImage(String uri, final ImageView view, final int loadingResId, final int errorResId){
        displayImage(uri, view, loadingResId, errorResId, false,true,0);
    }

    /**
     * 加载圆角图片
     * @param uri
     * @param view
     * @param loadingResId
     * @param errorResId
     */
    public  static  void displayRoundImage(String uri, final ImageView view, final int loadingResId, final int errorResId,int roundAngle){
        displayImage(uri, view, loadingResId, errorResId, false,false,roundAngle);
    }

    /**
     * 在ImageView中显示图像
     */
    private static void displayImage(String uri, final ImageView view, final int loadingResId, final int errorResId, boolean loadAnimation,boolean isCircleImage,int roundAngle) {
        final String uriFix = fixUrlString(uri);
        boolean isFile = !TextUtils.isEmpty(uriFix) && uriFix.startsWith("file://");
        DisplayImageOptions dio = getDefaultDisplayImageOptionsBuilder().
                imageScaleType(ImageScaleType.IN_SAMPLE_INT).
                showImageOnLoading(loadingResId).showImageOnFail(errorResId).showImageForEmptyUri(errorResId)
                .displayer(loadAnimation ? new FadeInBitmapDisplayer(1000) : new SimpleBitmapDisplayer())
                .displayer(isCircleImage?new CircleBitmapDisplayer():new SimpleBitmapDisplayer())
                .displayer(roundAngle!=0?new RoundedBitmapDisplayer(roundAngle):isCircleImage?new CircleBitmapDisplayer():new SimpleBitmapDisplayer())
                .cacheOnDisk(!isFile).cacheInMemory(true).build();
            getImageLoader().displayImage(uriFix, view, dio);
    }


    private static void displayImage(String uri, final ImageView view, final Drawable loadingBitmap, final Drawable errorBitmap) {
        displayImage(uri, view, loadingBitmap, errorBitmap, null);
    }

    /**
     * 在ImageView中显示图像
     */
    private static void displayImage(String uri, final ImageView view, final Drawable loadingBitmap, final Drawable errorBitmap,ImageView a) {
        final String uriFix = fixUrlString(uri);
        boolean isFile = !TextUtils.isEmpty(uriFix) && uriFix.startsWith("file://");

        DisplayImageOptions dio = getDefaultDisplayImageOptionsBuilder().showImageOnLoading(loadingBitmap).showImageOnFail(errorBitmap).showImageForEmptyUri(errorBitmap)
                .cacheOnDisk(!isFile).cacheInMemory(!isFile).build();
        getImageLoader().displayImage(uriFix, view, dio);
    }

    /**
     * 获取ImageLoader实例
     */
    public synchronized static ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            // 使用最大可用内存值的1/8作为缓存的大小。
            int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
            File cacheDir = new File(getCacheDir(), "images");
            Executor executor = CachedThreadPool.getInstance().getExecutor();
            DisplayImageOptions dio = getDefaultDisplayImageOptionsBuilder().build();

            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(MyApplication.getInstance());
            builder.taskExecutor(executor).taskExecutorForCachedImages(executor);
            builder.threadPoolSize(3);
            builder.threadPriority(Thread.NORM_PRIORITY - 2);
            builder.memoryCache(new UsingFreqLimitedMemoryCache(memoryCacheSize));
            builder.memoryCacheExtraOptions(480, 800); // 内存缓存图片最大大小
            builder.denyCacheImageMultipleSizesInMemory();
            builder.diskCache(new UnlimitedDiskCache(cacheDir));
            builder.defaultDisplayImageOptions(dio);
            builder.imageDownloader(new AuthImageDownloader(MyApplication.getInstance()));
            ImageLoaderConfiguration ilc = builder.build();

            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ilc);
        }
        return mImageLoader;
    }

    /**
     * 获取缓存路径（优先返回存储卡中的目录）
     */
    public static File getCacheDir(){
        Context ctx = MyApplication.getInstance();
        if (2 == hasExternalStorage()) {
            return ctx.getExternalCacheDir();
        } else {
            return ctx.getCacheDir();
        }
    }
    /**
     * 检索当前系统是否包含扩展存储卡
     *
     * @return 0:没有存储卡|1:有只读存储卡|2:有可读写存储卡
     * */
    public static int hasExternalStorage() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            return 2;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return 1;
        } else {
            // 其它状态是错误的，即不能读也不能写
            return 0;
        }
    }


    private static String fixUrlString(String url) {
        if (!TextUtils.isEmpty(url) && url.charAt(0) == '/')
            return "file://" + url;
        return url;
    }

    private static DisplayImageOptions.Builder getDefaultDisplayImageOptionsBuilder() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true);
    }

}
