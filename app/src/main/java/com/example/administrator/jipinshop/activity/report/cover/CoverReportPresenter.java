package com.example.administrator.jipinshop.activity.report.cover;

import android.app.Dialog;

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
 * @create 2019/5/22
 * @Describe
 */
public class CoverReportPresenter {

    private Repository mRepository;
    private CoverReportView mView;

    public void setView(CoverReportView view) {
        mView = view;
    }

    @Inject
    public CoverReportPresenter(Repository repository) {
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
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    if(imageBean.getCode() == 0){
                        if(mView != null){
                            mView.uploadPicSuccess(imageBean.getData(),file);
                        }
                    }else {
                        if (mView != null){
                            mView.uploadPicFailed(imageBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    if (mView != null){
                        mView.uploadPicFailed(throwable.getMessage());
                    }
                });
    }

    /**
     * 保存
     */
    public void saveReport(String trialId, String title, String img , String content, LifecycleTransformer<SuccessBean> transformer){
        mRepository.saveReport(trialId, title, img, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessSave();
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 发布
     */
    public void submitReport(String trialId, String title, String img , String content, LifecycleTransformer<SuccessBean> transformer){
        mRepository.submitReport(trialId, title, img, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessSave();
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
