package com.example.administrator.jipinshop.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/14
 * @Describe
 */
public class ShareUtils {


    private Context mContext;
    private SHARE_MEDIA mSHARE_media;
    private Repository mRepository;
    private LifecycleTransformer<TaskFinishBean> mTransformer;

    public ShareUtils(Context context,SHARE_MEDIA SHARE_media,LifecycleTransformer<TaskFinishBean> transformer,Repository repository) {
        mContext = context;
        mSHARE_media = SHARE_media;
        mRepository = repository;
        mTransformer = transformer;
    }

    /**
     * 分享链接
     */
    public void shareWeb(final Activity activity, String WebUrl, String title, String description, String imageUrl, int imageID) {
        taskFinish(mTransformer);
        UMWeb web = new UMWeb(WebUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        if (TextUtils.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(activity, imageID));  //本地缩略图
        } else {
            web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        }
        new ShareAction(activity)
                .setPlatform(mSHARE_media)//传入平台
                .withMedia(web)
                .setCallback(shareListener)//分享回调
                .share();
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.show("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.show("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.show("分享取消");
        }
    };

    /**
     * 分享获取极币
     */
    public void taskFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> {

                }, throwable ->{

                });
    }
}
