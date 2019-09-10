package com.example.administrator.jipinshop.activity.info;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 莫小婷
 * @create 2018/9/29
 * @Describe
 */
public class MyInfoPresenter {

    Repository mRepository;
    private MyInfoView mView;

    public void setView(MyInfoView view) {
        mView = view;
    }

    @Inject
    public MyInfoPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void SaveUserInfo(String type, String content, LifecycleTransformer<SuccessBean> transformer, Dialog dialog){
        Map<String,String> map = new HashMap<>();
        if(type.equals("2")){
            map.put("gender",content);
        }else if(type.equals("3")){
            map.put("birthday",content);
        }else if(type.equals("4")){
            //上传头像
            map.put("avatar",content);
        }else if (type.equals("5")){
            //上传背景
            map.put("bgImg",content);
        }
        mRepository.userUpdate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView != null){
                        if(type.equals("3")){
                            mView.EditBirthDaySuccess(successBean,content);
                        }else if(type.equals("2")){
                            mView.EditGenderSuccess(successBean,content);
                        }else if(type.equals("4")){
                            mView.EditUserNickImgSuc(successBean,content);
                        }else if (type.equals("5")){
                            mView.EditUserBg(successBean,content);
                        }
                    }
                }, throwable -> {

                });
    }

    /**
     * 上传图片
     */
    public void importCustomer(LifecycleTransformer<ImageBean> ransformer,Dialog mDialog, File file){
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
                        if(mDialog != null && mDialog.isShowing()){
                            mDialog.dismiss();
                        }
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

}
