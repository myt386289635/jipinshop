package com.example.administrator.jipinshop.activity.sign.market;

import android.app.Dialog;
import android.util.Log;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 莫小婷
 * @create 2020/7/13
 * @Describe
 */
public class MarketPresenter {

    private Repository mRepository;
    private MarketView mView;

    public void setView(MarketView view) {
        mView = view;
    }

    @Inject
    public MarketPresenter(Repository repository) {
        mRepository = repository;
    }


    /**
     * 上传图片
     */
    public void importCustomer(LifecycleTransformer<ImageBean> ransformer, Dialog mDialog, File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        mRepository.importCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(imageBean -> {
                    if(imageBean.getCode() == 0){
                        if(mView != null){
                            mView.uploadPicSuccess(mDialog,imageBean.getData());
                        }
                    }else {
                        if (mView != null){
                            mView.uploadPicFailed(imageBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if (mView != null){
                        mView.uploadPicFailed(throwable.getMessage());
                    }
                });
    }

    public void feedBook(String content,LifecycleTransformer<SuccessBean> transformer){
        mRepository.feedBack(content,"2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode()== 0){
                        mView.OpinionSuccess(content);
                    }else {
                        mView.uploadPicFailed(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.uploadPicFailed(throwable.getMessage());
                });
    }
}
